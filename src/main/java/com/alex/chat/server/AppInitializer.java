package com.alex.chat.server;

import java.io.IOException;

public class AppInitializer {
    public static void main(String[] args) throws IOException {
        ChatServer.getInstance().run();
    }
}
