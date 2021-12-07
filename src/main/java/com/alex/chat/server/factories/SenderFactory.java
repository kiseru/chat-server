package com.alex.chat.server.factories;

import com.alex.chat.server.models.User;
import com.alex.chat.server.services.UserMessageSender;

import javax.ejb.Stateless;
import java.io.PrintWriter;

/**
 * Фабрика создания экземпляров класса для отправки сообщений
 */
@Stateless
public class SenderFactory {
    public Runnable getSender(PrintWriter writer, User user) {
        return new UserMessageSender(writer, user);
    }
}