package com.alex.chat.server.messagesservice;

import java.util.LinkedList;

public class Queue<T> {

    private final LinkedList<T> queue;

    public Queue() {
        queue = new LinkedList<>();
    }

    public synchronized void push(T value) {
        queue.add(value);
    }

    public synchronized T pop() {
        return queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
