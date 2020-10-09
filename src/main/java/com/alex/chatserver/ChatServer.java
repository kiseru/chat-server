package com.alex.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private static ChatServer INSTANCE;

    private final Map<String, Group> groups = new HashMap<>();

    private ChatServer() {
    }

    public static ChatServer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatServer();
        }

        return INSTANCE;
    }

    public void run() throws IOException {

        // Устанавливаем порт для получения сообщений
        ServerSocket server = new ServerSocket(5003);

        // Выводим сообщение о том, что сервер начал работу
        System.out.println("Сервер запущен!");

        while (true) {

            // Ждем новое подключение
            Socket socket = server.accept();

            // Создаем пользователя
            User user = new User(socket);
        }
    }

    public Group getGroup(String groupName) {
        groups.computeIfAbsent(groupName, this::createGroup);
        return groups.get(groupName);
    }

    public void removeUser(Group group) {
        if (!groups.containsKey(group.getName())) {
            return;
        }

        groups.remove(group.getName());
        String message = String.format("Группа %s удалена.", group.getName());
        System.out.println(message);
    }

    private Group createGroup(String groupName) {
        Group group = new Group(groupName);
        String message = String.format("Группа %s создана.", group.getName());
        System.out.println(message);
        return group;
    }
}
