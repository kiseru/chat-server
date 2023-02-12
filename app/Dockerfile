FROM openjdk:11

ENV WORK_DIR=CharServer

RUN mkdir $WORK_DIR

WORKDIR $WORK_DIR

ADD ./build .

EXPOSE 5003

CMD "./install/ChatServer/bin/ChatServer"