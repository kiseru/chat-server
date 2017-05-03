FROM maven:3.5

COPY . src/

WORKDIR src/
RUN mvn clean compile assembly:single

EXPOSE 5003

CMD java -jar target/chat-server-jar-with-dependencies.jar