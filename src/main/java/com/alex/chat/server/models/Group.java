package com.alex.chat.server.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class Group {
    @Getter
    private final String name;

    private final Set<User> users = new HashSet<>();

    /**
     * Добавляет пользователя в группу
     *
     * @param user добавляемый в группу пользователь
     * @throws NullPointerException если {@code user} является {@code null}
     */
    public void addUser(User user) {
        Objects.requireNonNull(user);
        String messageText;
        synchronized (users) {
            users.add(user);
            messageText = String.format("%s добавился в группу", user.getName());
            sendMessageFromServer(messageText);
        }
        log.info("{} {}", messageText, name);
    }

    /**
     * Кладет каждому пользователю группы сообщение от имени сервера в очередь
     *
     * @param messageText текст отправляемого сообщения
     * @throws NullPointerException если {@code messageText} является {@code null}
     */
    public void sendMessageFromServer(String messageText) {
        Objects.requireNonNull(messageText);
        Message message = new Message(messageText);
        sendMessage(message);
    }

    /**
     * Кладет каждому пользователю группы сообщение в очередь
     *
     * @param message отправляемое сообщение
     * @throws NullPointerException если {@code message} является {@code null}
     */
    public void sendMessage(Message message) {
        Objects.requireNonNull(message);
        users.forEach(user -> user.sendMessage(message));
    }
}
