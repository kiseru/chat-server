package com.alex.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private static int users = 0;

    private String userName;
    private BufferedReader in;
    private PrintWriter out;
    private MessagesSender messagesSender;

    public Client(Socket socket, MessagesSender messagesSender) throws IOException {
        users++;
        this.userName = "user" + users;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.messagesSender = messagesSender;
    }


    public String getUserName() {
        return userName;
    }

    public void sendMessage(Message message) {
        out.println(message);
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

                    // Remove this client from messagesSender
                    messagesSender.removeClient(this);

                    break;
                }

                // Getting message and send it to our queue for next resending to other users
                Message newMessage = new Message(userName, input);
                messagesSender.addMessage(newMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
