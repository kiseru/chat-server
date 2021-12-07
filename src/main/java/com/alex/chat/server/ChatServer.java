package com.alex.chat.server;

import com.alex.chat.server.factories.ReceiverFactory;
import com.alex.chat.server.factories.SenderFactory;
import com.alex.chat.server.models.Group;
import com.alex.chat.server.models.User;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class ChatServer {
    private final Map<String, Group> groups = new ConcurrentHashMap<>();

    private final Executor executor = Executors.newCachedThreadPool();

    @Inject
    private ReceiverFactory receiverFactory;

    @Inject
    private SenderFactory senderFactory;

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
        String userName;
        try {
            userName = reader.readLine();
        } catch (IOException cause) {
            throw new IOException("Не удалось получить имя пользователя", cause);
        }

        String groupName;
        try {
            groupName = reader.readLine();
        } catch (IOException cause) {
            throw new IOException("Не удалось получить имя группы", cause);
        }

        Group group = getGroup(groupName);
        User user = new User(userName, group);
        group.addUser(user);
        return user;
    }

    /**
     * Возвращает группу из {@code groups}.
     * Если её там нет, то создает новую и добавляет её в {@code groups}.
     *
     * @param groupName название группы
     * @return группу с переданным названием
     * @throws NullPointerException если {@code groupName} является {@code null}
     */
    private Group getGroup(String groupName) {
        Objects.requireNonNull(groupName);
        return groups.computeIfAbsent(groupName, this::createGroup);
    }

    /**
     * Создает группу с переданным именем
     *
     * @param groupName имя группы
     * @return созданная группа
     */
    private Group createGroup(String groupName) {
        Objects.requireNonNull(groupName);
        Group group = new Group(groupName);
        log.info("Группа {} создана", group.getName());
        return group;
    }
}
