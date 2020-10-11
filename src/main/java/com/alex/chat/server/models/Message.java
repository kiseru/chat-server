package com.alex.chat.server.models;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Message {
    private final String from;

    private final String message;

    public Message(String message) {
        this("Сервер", message);
    }

    @Override
    public String toString() {
        return String.format("%s::%s", this.from, this.message);
    }
}
