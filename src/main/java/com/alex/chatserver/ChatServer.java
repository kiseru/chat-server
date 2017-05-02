package com.alex.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {

    private static List<ClientThread> clients = new LinkedList<ClientThread>();

    public static void run() throws SQLException, IOException, InterruptedException {

        ServerSocket server = new ServerSocket(5003);

        System.out.println("Server is up!");

        while (true) {

            // Waiting for new client
            Socket client = server.accept();

            // Creating new Thread for client-server connection and run it
            ClientThread clientThread = new ClientThread(client);
            clientThread.start();

            // Information about connection
            String userName = clientThread.getUserName();
            String message = String.format("%s joined the chat", userName);
            System.out.println(message);

            // Add clients to our collection
            clients.add(clientThread);
        }
    }
}
