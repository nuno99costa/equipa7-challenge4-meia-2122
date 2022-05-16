FROM python:alpine3.15

WORKDIR /code

COPY . .

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

CMD [ "python", "main.py" ]