package com.alex.chat.server.services;

import com.alex.chat.server.model.Message;
import com.alex.chat.server.models.User;
import lombok.RequiredArgsConstructor;

import java.io.PrintWriter;

public class UserMessageSender implements Runnable {
    private final PrintWriter writer;

    private final User user;

    public UserMessageSender(PrintWriter writer, User user) {
        this.writer = writer;
        this.user = user;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Message message = user.pollMessage();
            writer.println(message);
            writer.flush();
        }
    }
}
