package com.alex.chat.server;

import com.alex.chat.server.messagesservice.Message;
import com.alex.chat.server.messagesservice.MessagesSender;

public class Group {

    // Отправщик сообщений для этой группы
    private final MessagesSender sender;
    private final String name;

    public Group(String name) {
        this.name = name;
        this.sender = new MessagesSender();
    }

    // Добавляем пользователя в группу
    public void addUser(User user) {

        // Добавляем в список пользователей для отправки клиенту сообщений других пользователей
        sender.addUser(user);

        // Информируем участников группы о присоединение нового пользователя
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

    public boolean isEmpty() {
        return sender.isEmpty();
    }

    public String getName() {
        return name;
    }
}
