FROM python:3.12-slim

COPY umb /app/umb
COPY requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

EXPOSE 8000

CMD ["uvicorn", "umb.v1.main:app", "--host", "0.0.0.0", "--port", "8000"]