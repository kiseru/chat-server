package com.alex.chatserver;

import java.util.LinkedList;
import java.util.List;

public class MessagesSender extends Thread {

    private Queue<Message> messagesQueue;
    private List<User> users;

    public MessagesSender() {
        messagesQueue = new Queue<>();
        users = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messagesQueue.push(message);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public void run() {
        while (true) {

            // If we have a message for resending resend it
            while (!messagesQueue.isEmpty()) {
                this.setPriority(MAX_PRIORITY);
                Message message = messagesQueue.pop();
                users.forEach(user -> user.sendMessage(message));
            }

            this.setPriority(NORM_PRIORITY);
        }
    }
}
