from langchain_openai import ChatOpenAI
from langchain_community .document_loaders import PyPDFLoader, S3FileLoader,DirectoryLoader
from langchain.text_splitter import CharacterTextSplitter
from langchain.vectorstores.chroma import Chroma
import chromadb
import uuid
from langchain_community.embeddings.gpt4all import GPT4AllEmbeddings
from langchain.prompts import PromptTemplate
from langchain.chains import RetrievalQA
from langchain_core.output_parsers import JsonOutputParser
from src.utils.outputParser import Project

from langchain_google_genai import ChatGoogleGenerativeAI


AWS_S3_ACCESS_KEY = "AKIAY42F3KJNL5JRGJMD"
AWS_S3_SECRET_ACCESS_KEY = "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"
AWS_S3_BUCKET_NAME = "tasksmart-development"
AWS_REGION = "ap-southeast-2"
seperators= ['\n','\n\n' ,'\r\n', '\r', ' ', '']

parser = JsonOutputParser(pydantic_object=Project)

def entry_db(project_id: str):
    loader = S3FileLoader("tasksmart-development",
                          key="projects/"+project_id+"/spe.pdf",
                          aws_access_key_id=AWS_S3_ACCESS_KEY,
                          aws_secret_access_key=AWS_S3_SECRET_ACCESS_KEY)
    document = loader.load()
    embedding_model = GPT4AllEmbeddings()
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

def create_prompt(parser):
    template = "You are an AI bot managing the project, requires at least 15 tasks, assigning tasks to groups based on the software development process (do not assign to groups such as in progress or in review,... because no tasks have started yet). Use this information: {context}. Answer the user query.\n{format_instructions}\n{question}\n."
    prompt = PromptTemplate(
        template=template,
        input_variables=['context','question'],
        partial_variables={"format_instructions": parser.get_format_instructions()}
    )
    return prompt

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
    def generate_task(project_id:str):
        db = entry_db(project_id)
        llm = load_llm()
        prompt = create_prompt(parser=parser)
        llm_chain = create_simple_chain(prompt, llm, db)

        project_query = "Requirements"
        response = llm_chain.invoke({'query': project_query})
        parser_output= parser.invoke(response.get('result'))
        return parser_output