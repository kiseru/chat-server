package com.alex.chat.server.messagesservice;

import com.alex.chat.server.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MessagesSender {
    private final Queue<Message> messagesQueue;

    private final List<User> users;

    public MessagesSender() {
        messagesQueue = new LinkedList<>();
        users = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messagesQueue.add(message);
        this.run();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    private void run() {
        while (!messagesQueue.isEmpty()) {
            Message message = messagesQueue.poll();
            users.forEach(user -> user.sendMessage(message));
        }
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }
}
