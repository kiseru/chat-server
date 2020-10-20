package com.alex.chat.server.services;

import com.alex.chat.server.models.Message;
import com.alex.chat.server.models.User;
import lombok.RequiredArgsConstructor;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class UserMessageSender implements Runnable {
    private final PrintWriter writer;

    private final User user;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Message message = user.pollMessage();
            writer.println(message);
            writer.flush();
        }
    }
}
