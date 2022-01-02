package com.alex.chat.server.models;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class User {
    private final String name;

    private final Group group;

    private final Queue<Message> messageQueue = new LinkedList<>();

    public User(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    /**
     * Добавляет сообщение в очередь для дальнейшей отправки
     *
     * @param message отправляемое сообщение
     * @throws NullPointerException если {@code message} является {@code null}
     */
    public void sendMessage(Message message) {
        Objects.requireNonNull(message);
        synchronized (messageQueue) {
            messageQueue.add(message);
            messageQueue.notifyAll();
        }
    }

    /**
     * Достает из очереди следующее для отправки сообщение
     *
     * @return следующее сообщение
     */
    public Message pollMessage() {
        synchronized (messageQueue) {
            try {
                while (messageQueue.isEmpty()) {
                    messageQueue.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return messageQueue.poll();
        }
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }
}
