package com.alex.chat.server;

import com.alex.chat.server.messagesservice.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User extends Thread {

    private String userName;
    private Group group;
    private BufferedReader in;
    private PrintWriter out;

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

                // Если пользователь пишет exit, то отключаем его от сервера
                if (input.equalsIgnoreCase("disconnect exit car movie guards")) {

                    // Выводим информацию о том, что пользователь покинул группу
                    String message = String.format("%s покинул чат", userName);
                    group.sendMessage(new Message(message));

                    // Удаляем его из списка участников группы
                    group.removeUser(this);
                    break;
                }

                // Отправляем сообщение пользователям группы
                Message newMessage = new Message(userName, input);
                group.sendMessage(newMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
