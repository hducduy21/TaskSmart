from langchain.prompts import PromptTemplate

class TSMPromt:
        
    def create_prompt_create_statement(parser):
        template = """You are an AI Database Bot. Your task is give user the query statements based on the following database: {context}. 
        SQL language: {question}
        Answer the user query.\n{format_instructions}\n."""
        
        prompt = PromptTemplate(
            template=template,
            input_variables=['context','question'],
            partial_variables={"format_instructions": parser.get_format_instructions()}
        )
        return prompt

    def create_prompt(parser):
        template = "You are an AI bot managing the project, requires at least 15 tasks, assigning tasks to groups based on the software development process (do not assign to groups such as in progress or in review,... because no tasks have started yet). Use this information: {context}. Answer the user query.\n{format_instructions}\n{question}\n."
        prompt = PromptTemplate(
            template=template,
            input_variables=['context','question'],
            partial_variables={"format_instructions": parser.get_format_instructions()}
        )
        return prompt

    def create_prompt_db_rag(parser):
        template = """Based on the table schema below:
        {schema}
        Answer the user query with {database} database language. If the question is not relevant, please return a comment: please ask database related questions.
        .\n{format_instructions}\n{question}\n"""
        prompt = PromptTemplate(
                template=template, 
                input_variables=['schema','question','database'],
                partial_variables={"format_instructions": parser.get_format_instructions()}
                )
        return prompt

    def create_prompt_handler_file(parser):
        template = """Based on the SQL statements below:
        {statements}
        Let's shorten it to only statements related to table structure such as creating tables, editing table structures
        .\n{format_instructions}\n"""
        prompt = PromptTemplate(
                template=template, 
                input_variables=['statements'],
                partial_variables={"format_instructions": parser.get_format_instructions()}
                )
        return prompt

    def create_prompt_query_statement(parser):
        template = """You are an AI Database Bot. Your task is give user the query statements based on the following database: {context}. 
        Answer the user query.\n{format_instructions}\n{question}\n."""
        
        prompt = PromptTemplate(
            template=template,
            input_variables=['context','question'],
            partial_variables={"format_instructions": parser.get_format_instructions()}
        )
        return prompt
    
    def get_RAG_URI_Prompt_Template(parser1, parser2,parser3):
        template1 = """Based on the table schema below:
        {schema}
        Answer the user query
        .\n{format_instructions}\n{question}\n"""
        prompt1 = PromptTemplate(
                template=template1, 
                input_variables=['schema','question'],
                partial_variables={"format_instructions": parser1.get_format_instructions()}
                )
        
        template2 = """Based on the query below:
        {query}
        Answer the user query. If the question is not relevant, please return a comment: please ask database related questions.
        .\n{format_instructions}\n"""
        prompt2 = PromptTemplate(
                template=template2, 
                input_variables=['query'],
                partial_variables={"format_instructions": parser2.get_format_instructions()}
                )
        
        template3 = """Based on the table schema below, question, sql query, and sql response, write a natural language response:
        {schema}
        
        SQL Query: {validate_query}
        SQL Response: {response}
        Answer the user query
        .\n{format_instructions}\n{question}\n"""
        prompt3 = PromptTemplate(
                template=template3, 
                input_variables=['schema','question', 'validate_query', 'response'],
                partial_variables={"format_instructions": parser3.get_format_instructions()}
                )
        return [prompt1, prompt2, prompt3]