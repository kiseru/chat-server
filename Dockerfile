FROM maven:3.5

COPY . src/

WORKDIR src/
RUN mvn package

CMD java -jar target/chat_server.jar