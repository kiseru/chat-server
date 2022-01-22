package com.alex.chat.server.services;

import com.alex.chat.server.model.Message;
import com.alex.chat.server.models.Group;
import com.alex.chat.server.models.User;

import java.io.BufferedReader;
import java.io.IOException;

public class UserMessageReceiver implements Runnable {
    private final BufferedReader reader;

    private final User user;

    public UserMessageReceiver(BufferedReader reader, User user) {
        this.reader = reader;
        this.user = user;
    }

    @Override
    public void run() {
        String input;
        try {
            while ((input = reader.readLine()) != null) {
                Message message = new Message(user.getName(), input);
                Group group = user.getGroup();
                group.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
