package com.alex.chatserver;

import java.util.LinkedList;
import java.util.List;

public class MessagesSender extends Thread {

    private Queue<Message> messagesQueue;
    private List<Client> clients;

    public MessagesSender() {
        messagesQueue = new Queue<>();
        clients = new LinkedList<>();
    }

    public void addMessage(Message message) {
        messagesQueue.push(message);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    @Override
    public void run() {
        while (true) {

            // If we have a message for resending resend it
            while (!messagesQueue.isEmpty()) {
                this.setPriority(MAX_PRIORITY);
                Message message = messagesQueue.pop();
                clients.forEach(client -> client.sendMessage(message));
            }

            this.setPriority(NORM_PRIORITY);
        }
    }
}
