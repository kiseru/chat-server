package com.alex.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private static int users = 0;

    private String userName;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket) throws IOException {
        users++;
        this.userName = "user" + users;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
    }


    public String getUserName() {
        return userName;
    }

    @Override
    public void run() {
        String input;
        try {
            // Read and write to the server console
            while ((input = in.readLine()) != null) {

                // Exit condition from chat
                if (input.equalsIgnoreCase("exit")) {

                    // Print leaving information
                    String message = String.format("%s leave the chat", userName);
                    System.out.println(message);

                    break;
                }

                System.out.println(userName + "::" + input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
