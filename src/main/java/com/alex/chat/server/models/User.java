package com.alex.chat.server.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@RequiredArgsConstructor
public class User {
    @Getter
    private final String name;

    @Getter
    private final Group group;

    private final Queue<Message> messageQueue = new LinkedList<>();

    /**
     * Добавляет сообщение в очередь для дальнейшей отправки
     *
     * @param message отправляемое сообщение
     * @throws NullPointerException если {@code message} является {@code null}
     */
    public void sendMessage(Message message) {
        Objects.requireNonNull(message);
        messageQueue.add(message);
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * Достает из очереди следующее для отправки сообщение
     *
     * @return следующее сообщение
     */
    public Message pollMessage() {
        return messageQueue.poll();
    }

    /**
     * Возвращает {@code true} если очередь пуста, иначе {@code false}
     *
     * @return {@code true} если очереди пуста, иначе {@code false}
     */
    public boolean isMessageQueueEmpty() {
        return messageQueue.isEmpty();
    }
}
