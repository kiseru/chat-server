package com.alex.chatserver;

public class Message {

    private String from;
    private String message;

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
