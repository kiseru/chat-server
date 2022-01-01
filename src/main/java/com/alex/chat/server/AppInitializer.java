package com.alex.chat.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class AppInitializer {
    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chat-server-context.xml");
        ChatServer server = (ChatServer) ctx.getBean("chatServer");
        server.run();
    }
}
