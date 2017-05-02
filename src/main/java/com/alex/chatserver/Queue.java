package com.alex.chatserver;

import java.util.LinkedList;

public class Queue<T> {

    private LinkedList<T> queue;

    public Queue() {
        queue = new LinkedList<T>();
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
