from langchain_community .document_loaders import S3FileLoader
from langchain.text_splitter import CharacterTextSplitter
from langchain_community.vectorstores.chroma import Chroma
from langchain.chains import RetrievalQA
from langchain_core.output_parsers import JsonOutputParser
from src.utils.outputParser import Project, DBCreateStatement, DBRAGStatement, IsSelectQuery, FileHanler, StatementRunable, DBRAGStatementRunable
from src.utils.RequestDTO import DBRagRequest
from langchain_core.runnables import RunnablePassthrough
from langchain_community.utilities.sql_database import SQLDatabase
from langchain_core.runnables import RunnablePassthrough

from langchain_google_genai.embeddings import GoogleGenerativeAIEmbeddings
from langchain_google_genai import ChatGoogleGenerativeAI

from src.utils.Prompts import TSMPromt


AWS_S3_ACCESS_KEY = "AKIAY42F3KJNL5JRGJMD"
AWS_S3_SECRET_ACCESS_KEY = "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"
AWS_S3_BUCKET_NAME = "tasksmart-development"
AWS_REGION = "ap-southeast-2"
seperators= ['\n','\n\n' ,'\r\n', '\r', ' ', '']

def entry_db(project_id: str):
    loader = S3FileLoader("tasksmart-development",
                          key="projects/"+project_id+"/spe.pdf",
                          aws_access_key_id=AWS_S3_ACCESS_KEY,
                          aws_secret_access_key=AWS_S3_SECRET_ACCESS_KEY)
    document = loader.load()
    embedding_model = GoogleGenerativeAIEmbeddings(model="models/embedding-001", google_api_key="AIzaSyBRmzrMqjc-FvOlPhHT_sFm1O1fbUme8Ec")
    text = ""
    for page in document:
        text += page.page_content

    text_splitter = CharacterTextSplitter(
        separator="CHAPTER",
        chunk_size=2000,
        chunk_overlap=50,
        is_separator_regex=False,
    )
    chunks = text_splitter.split_text(text)

    chroma = Chroma.from_texts(texts=chunks, embedding=embedding_model)
    print(chroma.similarity_search("Requirements"))
    return chroma

def load_llm():
    # llm = ChatOpenAI(api_key="sk-proj-hgdE6s8Y9IS9sYnDzUELT3BlbkFJfrswtg4SAWNnZwLL7Wve")
    llm = ChatGoogleGenerativeAI(model="gemini-pro", google_api_key="AIzaSyBRmzrMqjc-FvOlPhHT_sFm1O1fbUme8Ec")
    return llm


def create_simple_chain(prompt, llm, db):
    chain = RetrievalQA.from_chain_type(
        llm=llm,
        chain_type="stuff",
        retriever = db.as_retriever(search_kwargs={"k": 1}),
        return_source_documents=False,
        chain_type_kwargs={"prompt": prompt}
    )
    return chain


class LLMChain:
    def generate_task(project_id: str):
        parser = JsonOutputParser(pydantic_object=Project)
        db = entry_db(project_id)
        llm = load_llm()
        prompt = TSMPromt.create_prompt(parser=parser)
        llm_chain = create_simple_chain(prompt, llm, db)

        project_query = "Requirements"
        response = llm_chain.invoke({'query': project_query})
        parser_output= parser.invoke(response.get('result'))
        return parser_output
    
    def generateStruture(project_id:str, database:str):
        parser = JsonOutputParser(pydantic_object=DBCreateStatement)
        
        db = entry_db(project_id)
        llm = load_llm()
        prompt = TSMPromt.create_prompt_create_statement(parser)
        
        project_query = "Requirements"
        llm_chain = create_simple_chain(prompt, llm, db)
        response = llm_chain.invoke({'query': project_query, 'question': database})
        
        parser_output= parser.invoke(response.get('result'))
        return parser_output
    
    def DBQueryStatementRAQ(request: DBRagRequest):
        parser = JsonOutputParser(pydantic_object=DBRAGStatement)
        
        llm = load_llm()
        prompt = TSMPromt.create_prompt_db_rag(parser)
    
        sql_chain = (
            prompt
            | llm.bind(stop=["\nSQL Result:"])
            | parser
        )
        
        response = sql_chain.invoke({'database': request.database, 'question': request.question, 'schema': request.context})
        
        return response
    
    def fileHandler(content):
        parser = JsonOutputParser(pydantic_object=FileHanler)
        
        llm = load_llm()
        prompt = TSMPromt.create_prompt_handler_file(parser)
    
        sql_chain = prompt | llm | parser
        
        response = sql_chain.invoke({'statements': content})
        
        return response
    
    def getDbStructureByUri(uri:str):
        db = SQLDatabase.from_uri(uri)
        schema = db.get_table_info_no_throw()
        return {"schema": schema}
    
    def runStatement(uri:str, statement:str):
        try:
            db = SQLDatabase.from_uri(uri)
        except Exception as e:
            return {"result": "Invalid URI"}
        try:
            db.run(statement)
            return {"result": "Statement executed successfully"}
        except Exception as e:
            return {"result": "Statement execution failed"}
    
    def DBQueryStatementRAQByURI(uri:str, question:str):
        db = SQLDatabase.from_uri(uri)
        llm = load_llm()
        
        parser1 = JsonOutputParser(pydantic_object=StatementRunable)
        parser2 = JsonOutputParser(pydantic_object=IsSelectQuery)
        parser3 = JsonOutputParser(pydantic_object=DBRAGStatementRunable)
        prompts = TSMPromt.get_RAG_URI_Prompt_Template(parser1,parser2,parser3)
        
        def get_schema(_):
            schema = db.get_table_info()
            return schema
        def run_query(query: str):
            if query.strip().lower().startswith("select"):
                print("running query: "+ query)
                return db.run(query)
            else:
                print("Cant't run other select statements")
                return "Cant't run other select statements"

        def get_statement(result):
            return result['statement']
        def get_runable(result):
            return result['runable']
        
        sql_chain = (
            RunnablePassthrough.assign(schema=get_schema)
            | prompts[0]
            | llm.bind(stop=["\nSQL Result:"])
            | parser1
        )
        
        checkquery_chain = (
            RunnablePassthrough.assign(query=get_schema)
            | prompts[1]
            | llm
            | parser2
        )
        
        full_chain = (
            RunnablePassthrough.assign(query=sql_chain)
            .assign(validate_query=lambda vars: get_statement(vars["query"]))
            # .assign(get_runable=checkquery_chain)
            # .assign(runable=lambda vars: get_runable(vars["get_runable"]))
            .assign(
                schema=get_schema,
                response=lambda vars: run_query(vars["validate_query"]),
            )
            | prompts[2]
            | llm
            | parser3
        )
        
        return full_chain.invoke({'question': question})