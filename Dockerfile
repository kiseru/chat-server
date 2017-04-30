FROM maven:3.5

COPY . src/

WORKDIR src/
RUN mvn clean compile assembly:single

CMD java -jar target/chat_server-jar-with-dependencies.jar