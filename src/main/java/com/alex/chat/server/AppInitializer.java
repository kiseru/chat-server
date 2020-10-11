package com.alex.chat.server;

import java.io.IOException;

public class AppInitializer {
    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        server.run();
    }
}
