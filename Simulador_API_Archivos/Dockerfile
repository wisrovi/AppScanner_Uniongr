# 1) indicamos la imagen base a usar
FROM faucet/python3

#Author and Maintainer
LABEL MAINTAINER wisrovi.rodriguez@gmail.com

# 2) creamos una carpeta para alojar los archivos del proyecto
WORKDIR /api_simulador

# 3) instalamos sudo y actualizamos
#RUN apt-get update -y

# 4) instalar dependencias del SO
#RUN apt-get -y install python3-pip

# 5) instalar dependencias de python
RUN pip3 install flask

# 6) copiamos la carpeta del codigo y todos sus recursos
COPY . .

CMD ["python3", "./app.py" ]
