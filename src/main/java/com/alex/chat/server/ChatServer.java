package com.alex.chat.server;

import com.alex.chat.server.factories.ReceiverFactory;
import com.alex.chat.server.factories.SenderFactory;
import com.alex.chat.server.models.User;
import com.alex.chat.server.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class ChatServer {
    private final Executor executor = Executors.newCachedThreadPool();

    private final GroupService groupService;

    private final ReceiverFactory receiverFactory;

    private final SenderFactory senderFactory;

    @Autowired
    public ChatServer(GroupService groupService, ReceiverFactory receiverFactory, SenderFactory senderFactory) {
        this.groupService = groupService;
        this.receiverFactory = receiverFactory;
        this.senderFactory = senderFactory;
    }

    /**
     * Запуск сервера
     */
    public void run() throws IOException {
        ServerSocket server = new ServerSocket(5003);
        log.info("Сервер запущен");
        while (!server.isClosed()) {
            Socket socket = server.accept();
            executor.execute(() -> handleConnection(socket));
        }
    }

    /**
     * Обработка подключения пользователя
     *
     * @param socket - сокет, подключаемого клиента
     */
    private void handleConnection(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            User user = authorizeUser(reader);
            executor.execute(receiverFactory.getReceiver(reader, user));
            executor.execute(senderFactory.getSender(writer, user));
        } catch (IOException e) {
            log.error("Проблемы с подключением к клиенту");
        }
    }

    /**
     * Получает от клиента имя пользователя и название группы для добавления
     *
     * @param reader входящий поток для чтения данных от клиента
     * @return сущность пользователя
     * @throws IOException если не удалось получить имя пользователя или название группы от клиента
     */
    private User authorizeUser(BufferedReader reader) throws IOException {
        String userName = readUserName(reader);
        String groupName = readGroupName(reader);
        return groupService.addUserToGroup(userName, groupName);
    }

    private String readUserName(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            log.error("Не удалось получить имя пользователя", e);
            throw new RuntimeException(e);
        }
    }

    private String readGroupName(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            log.error("Не удалось получить имя группы", e);
            throw new RuntimeException(e);
        }
    }
}
