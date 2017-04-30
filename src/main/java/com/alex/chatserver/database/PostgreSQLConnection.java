package com.alex.chatserver.database;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLConnection {

    private static PostgreSQLConnection postgreSQLConnection;
    private static Connection connection;

    private PostgreSQLConnection() {
        Driver driver = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/chat_server",
                    "alex",
                    "big_secret"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Connection with PostgreSQL up!");
    }

    public static Connection getConnection() {
        if (postgreSQLConnection == null) {
            synchronized (PostgreSQLConnection.class) {
                if (postgreSQLConnection == null) {
                    postgreSQLConnection = new PostgreSQLConnection();
                }
            }
        }

        return connection;
    }
}
