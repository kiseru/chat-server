package com.alex.chat.server;

import com.alex.chat.server.models.Group;
import com.alex.chat.server.models.User;
import com.alex.chat.server.services.UserMessageReceiver;
import com.alex.chat.server.services.UserMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    private final Map<String, Group> groups = new ConcurrentHashMap<>();

    private final Executor executor = Executors.newCachedThreadPool();

    /**
     * Запуск сервера
     */
    public void run() throws IOException {
        ServerSocket server = new ServerSocket(5003);
        logger.info("Сервер запущен");
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
            createUserMessageReceiver(reader, user);
            createUserMessageSender(writer, user);
        } catch (IOException e) {
            logger.error("Проблемы с подключением к клиенту");
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
        logger.info("Группа {} создана", group.getName());
        return group;
    }

    /**
     * Создает нить для чтение данных пользователя
     *
     * @param reader входящий поток
     * @param user пользователь, у которого получаются данные
     */
    private void createUserMessageReceiver(BufferedReader reader, User user) {
        UserMessageReceiver receiver = new UserMessageReceiver(reader, user);
        Thread thread = new Thread(receiver);
        thread.start();
    }

    /**
     * Создает нить для отправки данных пользователю
     *
     * @param writer исходящий поток
     * @param user пользователь, которому отправляются данные
     */
    private void createUserMessageSender(PrintWriter writer, User user) {
        UserMessageSender sender = new UserMessageSender(writer, user);
        Thread senderThread = new Thread(sender);
        senderThread.start();
    }
}
