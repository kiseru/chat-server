package com.alex.chat.server.models;

import com.alex.chat.server.ChatServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User extends Thread {
    private final String userName;

    private final Group group;

    private final BufferedReader in;

    private final PrintWriter out;

    public User(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        userName = in.readLine();
        group = ChatServer.getInstance().getGroup(in.readLine());
        group.addUser(this);
        this.start();
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
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("disconnect exit car movie guards")) {
                    String message = String.format("%s покинул чат", userName);
                    group.sendMessage(new Message(message));
                    group.removeUser(this);
                    break;
                }

                Message newMessage = new Message(userName, input);
                group.sendMessage(newMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
