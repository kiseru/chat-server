package com.alex.chat.server.messagesservice;

import com.alex.chat.server.User;

import java.util.LinkedList;
import java.util.List;

public class MessagesSender {

    private final Queue<Message> messagesQueue;
    private final List<User> users;

    public MessagesSender() {
        messagesQueue = new Queue<>();
        users = new LinkedList<>();
    }

    public void addMessage(Message message) {

        // При отправке сообщения добавляем его в очередь для отправки и запускаем рассыльщик
        messagesQueue.push(message);
        this.run();
    }

    // Добавляем пользователя в список рассыльщика
    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    private void run() {

        // Запускаем наш рассыльщик
        while (!messagesQueue.isEmpty()) {
            Message message = messagesQueue.pop();
            users.forEach(user -> user.sendMessage(message));
        }
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }
}
