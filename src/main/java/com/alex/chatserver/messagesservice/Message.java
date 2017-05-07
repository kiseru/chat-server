package com.alex.chatserver.messagesservice;

public class Message {

    private final String from;
    private final String message;

    public Message(String message) {
        this("Сервер", message);
    }

    public Message(String from, String message) {

        this.from = from;
        this.message = message;
    }

    @Override
    public String toString() {
        // return a message format (userName::message)
        return String.format("%s::%s", this.from, this.message);
    }
}
