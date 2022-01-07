package com.alex.chat.server;

import com.alex.chat.server.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class AppInitializer {
    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ChatServer server = (ChatServer) ctx.getBean("chatServer");
        server.run();
    }
}
