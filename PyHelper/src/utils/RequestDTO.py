from pydantic import BaseModel, Field

class DBRagRequest(BaseModel):
    context: str
    database: str = Field(default='MySQL')
    question: str
    
class URIRequest(BaseModel):
    uri: str
    
class UriRagRequest(BaseModel):
    uri: str
    question: str
    
class RunStatementRequest(BaseModel):
    uri: str
    statement: str