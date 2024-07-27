from fastapi import FastAPI, Response, File, UploadFile, HTTPException
from src.utils.RequestDTO import DBRagRequest, URIRequest, UriRagRequest, RunStatementRequest
from src.utils.aws import AWS
from src.features.ProjectBot import LLMChain
import py_eureka_client.eureka_client as eureka_client
app = FastAPI()

eureka_server = "http://localhost:8761/eureka"
app_name = "PyHelper"
app_port = 8807

# @app.on_event("startup")
# async def startup_event():
#     print("Registering with Eureka")
#     await eureka_client.init_async(eureka_server=eureka_server,
#                                        app_name=app_name)

@app.get("/read-s3/{file_name}")
async def read_from_s3(file_name: str):
    file_content, success = AWS.read_from_s3(file_name)
    if success:
        return Response(content=file_content, media_type="image/jpeg")  # Adjust media_type as necessary
    else:
        return {"error": file_content}

@app.post("/put-s3")
async def upload_file(file: UploadFile = File(...)):
    return AWS.put_to_s3(file)

@app.get("/api/pyhelper/projects/{project_id}/generate-task")
async def generate_content(project_id: str):
    return LLMChain.generate_task(project_id)

@app.get("/api/pyhelper/projects/{project_id}/generate-structure")
async def generate_content(project_id: str, database:str):
    return LLMChain.generateStruture(project_id, database)

@app.post("/api/pyhelper/database-rag")
async def generate_content(request: DBRagRequest):
    print(request)
    return LLMChain.DBQueryStatementRAQ(request)

@app.post("/api/pyhelper/file-handler")
async def upload_file(file: UploadFile = File(...)):
    content = await file.read()
    return LLMChain.fileHandler(content.decode("utf-8"))

@app.post("/api/pyhelper/get-structure-by-uri")
async def getDbStructureByUri(request: URIRequest):
    return LLMChain.getDbStructureByUri(request.uri)

@app.post("/api/pyhelper/database-rag-by-uri")
async def getDbStructureByUri(request: UriRagRequest):
    return LLMChain.DBQueryStatementRAQByURI(request.uri, request.question)

@app.post("/api/pyhelper/run-statement")
async def runStatement(request: RunStatementRequest):
    return LLMChain.runStatement(request.uri, request.statement)
