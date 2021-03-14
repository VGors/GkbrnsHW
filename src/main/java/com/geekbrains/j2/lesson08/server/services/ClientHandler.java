package com.geekbrains.j2.lesson08.server.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private ServerEngine serverEngine;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String name;

    public ClientHandler(ServerEngine serverEngine, Socket socket) {
        try {
            this.serverEngine = serverEngine;
            this.socket = socket;
            this.dos = new DataOutputStream(this.socket.getOutputStream());
            this.dis = new DataInputStream(this.socket.getInputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String str) {
        try {
            dos.writeUTF(str);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    private void authentication() throws IOException {
        while (true) {
            String authStr = dis.readUTF();
            if (authStr.startsWith("/auth")) {
                String[] arrStr = authStr.split("\\s");
                String nick = "";
                if (arrStr.length == 3) {
                    nick = serverEngine
                            .getAService()
                            .getNickByLogPass(arrStr[1], arrStr[2]);
                }
                if (!nick.isEmpty()) {
                    if (!serverEngine.isBusy(nick)) {
                        sendMessage("/authok " + nick);
                        this.name = nick;
                        serverEngine.sendBroadcast(nick + " joined to chat");
                        System.out.println("Client: \"" + nick + "\" accepted");
                        serverEngine.addSubscriber(this);
                        return;
                    } else {
                        sendMessage("Login: " + nick + " already joined");
                    }
                } else {
                    sendMessage("Wrong login or password");
                }
            }
        }
    }

    private void readMessage() throws IOException {
        while (true) {
            String inputMessage = dis.readUTF();
            System.out.println(name + ": " + inputMessage);
            if (inputMessage.startsWith("/")) {
                if (inputMessage.startsWith("/cr")) {    //clients request
                    serverEngine.onlineClientsList(this);
                }
                if (inputMessage.startsWith("/w")) {    //private message
                    String[] strings = inputMessage.split("\\s", 3);
                    if (strings.length == 3) {
                        serverEngine.directSend(this, strings[1], strings[2]);
                    } else {
                        sendMessage("Message didn't send, try again");
                    }
                }
                if (inputMessage.equals("/q")) {        //quit
                    sendMessage(name + " left the chat");
                    return;
                }
            } else {
                serverEngine.sendBroadcast(name + ": " + inputMessage);
            }
        }
    }

    void closeConnection() {
        serverEngine.remSubscriber(this);
        serverEngine.sendBroadcast(name + " has disconnected");
        try {
            dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            dos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            dis.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
