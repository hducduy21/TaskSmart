//run appl
.venv\Scripts\python.exe -m uvicorn main:app --reload --port 8807

//activate venv
.venv\Scripts\activate

//venv -> requirements.txt
pip freeze > requirements.txt
