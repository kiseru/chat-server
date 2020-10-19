package com.alex.chat.server.services;

import com.alex.chat.server.models.Message;
import com.alex.chat.server.models.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class UserMessageSender implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(UserMessageSender.class);

    private final PrintWriter writer;

    private final User user;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = user.pollMessage();
                writer.println(message);
                writer.flush();
            }
        } catch (InterruptedException e) {
            logger.error("Поток прерван", e);
        }
    }
}
