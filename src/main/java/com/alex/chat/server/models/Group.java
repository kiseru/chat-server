package com.alex.chat.server.models;

import com.alex.chat.server.ChatServer;
import com.alex.chat.server.messagesservice.MessagesSender;

public class Group {
    private final MessagesSender sender;

    private final String name;

    public Group(String name) {
        this.name = name;
        this.sender = new MessagesSender();
    }

    public void addUser(User user) {
        sender.addUser(user);
        String message = String.format("%s добавился в группу.", user.getUserName());
        this.sendMessage(new Message(message));
    }

    public void sendMessage(Message message) {
        sender.addMessage(message);
    }

    public void removeUser(User user) {
        sender.removeUser(user);
        ChatServer.getInstance().removeUser(this);
    }

    public String getName() {
        return name;
    }
}
