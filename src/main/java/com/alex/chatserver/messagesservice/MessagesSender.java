package com.alex.chatserver.messagesservice;

import com.alex.chatserver.User;

import java.util.LinkedList;
import java.util.List;

public class MessagesSender {

    private Queue<Message> messagesQueue;
    private List<User> users;

    public MessagesSender() {
        messagesQueue = new Queue<>();
        users = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messagesQueue.push(message);
        this.run();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void run() {

        // If we have a message for resending resend it
        while (!messagesQueue.isEmpty()) {
            Message message = messagesQueue.pop();
            users.forEach(user -> user.sendMessage(message));
        }
    }
}
