from langchain_core.pydantic_v1 import BaseModel, Field

class CheckList(BaseModel):
    name: str = Field(description="The name of the to-do")

class CheckListGroup(BaseModel):
    name: str = Field(description="The name of the check list, to-do group")
    checkList: list[CheckList] = Field(description="The checklists/to-do in the group")

class Card(BaseModel):
    name: str = Field(description="The title of the card/task")
    description: str = Field(description="The description of the card/task")
    checkLists: list[CheckList] = Field(description="The checklists in the card, a group to do in task, card")

class ListCard(BaseModel):
    name: str = Field(description="The name of the list")
    cards: list[Card] = Field(description="Cards are the most granular element of the system, representing individual tasks or work items. Cards contain detailed information about the task, including titles, descriptions, due dates, and assigned users.")

class Project(BaseModel):
    listCards: list[ListCard] = Field(description="Lists are used to represent stages of a project, categories of tasks, or any other logical grouping that makes sense for the project at hand.")

class Statement(BaseModel):
    statement: str = Field(description="The statement to be executed")
    title: str = Field(description="The title of the statement")
    
class DBRAGStatementRunable(BaseModel):
    statement: str = Field(description="The statement to be executed")
    title: str = Field(description="The title of the statement")
    result: str = Field(description="The content of SQL response, if the response cant be run, return blank")

class StatementRunable(BaseModel):
    statement: str = Field(description="The statement to be executed")
    title: str = Field(description="The title of the statement")
    
class DBCreateStatement(BaseModel):
    statement: str = Field(description="The create table statements to be executed, format the statement with a newline character, each field of a table is a line")
    database: str = Field(description="Type of database")
    
class DBRAGStatement(BaseModel):
    statements: list[Statement] = Field(description="List of database statement used to execute user requests")
    database: str = Field(description="Type of database")
    
class FileHanler(BaseModel):
    result: str = Field(description="The result statements that only statements related to table structure")
    
class IsSelectQuery(BaseModel):
    runable: bool = Field(description="Is this the select statement")
    