FROM python:3.11-slim

COPY requirements.txt .

RUN pip install -r requirements.txt

COPY . .

EXPOSE 8807

CMD ["python", "-m", "uvicorn", "main:app", "--reload", "--port", "8807"]