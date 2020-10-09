package com.alex.chat.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

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
        ServerSocket server = new ServerSocket(5003);
        logger.info("Сервер запущен");
        while (true) {
            Socket socket = server.accept();
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
        logger.info("Группа {} удалена", group.getName());
    }

    private Group createGroup(String groupName) {
        Group group = new Group(groupName);
        logger.info("Группа {} создана", group.getName());
        return group;
    }
}
