package com.alex.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ChatServer {

    private final static LinkedList<Group> GROUPS = new LinkedList<>();

    public static void run() throws IOException {

        // Устанавливаем порт для получения сообщений
        ServerSocket server = new ServerSocket(5003);

        System.out.println("Сервер запущен!");

        while (true) {

            // Ждем новое подключение
            Socket socket = server.accept();

            // Создаем пользователя
            User user = new User(socket);
        }
    }

    public static Group getGroup(String groupName) {

        // Узнаем существует ли группа
        boolean isExisted = GROUPS.stream()
                .anyMatch(group -> group.getName().equals(groupName));

        // Если существует, то возращаем ее, иначе создаем новую, добавляя в список групп
        if (isExisted) {
            return GROUPS.stream()
                    .filter(group -> group.getName().equals(groupName))
                    .findFirst().get();
        } else {
            Group group = new Group(groupName);
            GROUPS.add(group);

            // Выводим сообщение на сервер о создании группы
            String message = String.format("Группа %s создана.", group.getName());
            System.out.println(message);

            return group;
        }
    }
}
