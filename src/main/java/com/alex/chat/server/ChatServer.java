package com.alex.chat.server;

import com.alex.chat.server.models.User;
import com.alex.chat.server.service.GroupService;
import com.alex.chat.server.service.ReceiverService;
import com.alex.chat.server.service.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class ChatServer {
    private final Executor executor = Executors.newCachedThreadPool();

    private final GroupService groupService;

    private final ReceiverService receiverService;

    private final SenderService senderService;

    @Autowired
    public ChatServer(GroupService groupService, ReceiverService receiverService, SenderService senderService) {
        this.groupService = groupService;
        this.receiverService = receiverService;
        this.senderService = senderService;
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
            executor.execute(receiverService.createReceiver(reader, user));
            executor.execute(senderService.createSender(writer, user));
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
