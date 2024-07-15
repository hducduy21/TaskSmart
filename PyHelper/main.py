from fastapi import FastAPI, Response, File, UploadFile, HTTPException
from src.utils.aws import AWS
from src.features.ProjectBot import LLMChain
import py_eureka_client.eureka_client as eureka_client
app = FastAPI()

eureka_server = "http://localhost:8761/eureka"
app_name = "PyHelper"
app_port = 8807

@app.on_event("startup")
async def startup_event():
    print("Registering with Eureka")
    await eureka_client.init_async(eureka_server=eureka_server,
                                       app_name=app_name)
@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}

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

