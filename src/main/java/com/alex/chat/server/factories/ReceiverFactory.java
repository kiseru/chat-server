package com.alex.chat.server.factories;

import com.alex.chat.server.models.User;
import com.alex.chat.server.services.UserMessageReceiver;

import java.io.BufferedReader;

/**
 * Фабрика создания экземпляров класса для получения сообщений
 */
public class ReceiverFactory {
    public Runnable getReceiver(BufferedReader reader, User user) {
        return new UserMessageReceiver(reader, user);
    }
}
