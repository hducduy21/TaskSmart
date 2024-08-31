//run appl
.tsmenv\Scripts\python.exe -m uvicorn main:app --reload --port 8807

//activate venv
.tsmenv\Scripts\activate

//venv -> requirements.txt
pip freeze > requirements.txt


python -m venv .tsmenv
