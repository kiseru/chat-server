package com.alex.chatserver;

import java.io.IOException;
import java.sql.SQLException;

public class AppInitializer {

    public static void main(String[] args) throws InterruptedException, SQLException, IOException {
        ChatServer.run();
    }
}
