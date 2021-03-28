package com.geekbrains.j3.lesson_03.server.services;

import com.geekbrains.j3.lesson_03.server.interfaces.Censurable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class ClientHandler {
    private ServerEngine serverEngine;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String nickName;

    public ClientHandler(ServerEngine serverEngine, Socket socket) {
        try {
            this.serverEngine = serverEngine;
            this.socket = socket;
            this.dos = new DataOutputStream(this.socket.getOutputStream());
            this.dis = new DataInputStream(this.socket.getInputStream());
            this.nickName = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (IOException | SQLException e) {
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void sendMessage(String str) {
        try {
            dos.writeUTF(serverEngine.msgFilter(str));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public String toString() {
        return nickName;
    }

    private void authentication() throws IOException {
        while (true) {
            String authStr = dis.readUTF();
            if (authStr.startsWith("/auth")) {
                String[] arrStr = authStr.split("\\s");
                String nick = "";
                if (arrStr.length == 3) {
                    nick = serverEngine.getNickByLogPass(arrStr[1], arrStr[2]);
                }
                if (!nick.isEmpty()) {
                    if (!serverEngine.isBusy(nick)) {
                        if (serverEngine.isExist(nick)) {
                            sendMessage("/authok " + nick);
                            this.nickName = nick;
                            serverEngine.sendBroadcast(nick + " joined to chat");
                            System.out.println("Client: \"" + nick + "\" accepted");
                            serverEngine.addSubscriber(this);
                            return;
                        } else {
                            sendMessage("Login: " + nick + " doesn't exist");
                        }
                    } else {
                        sendMessage("Login: " + nick + " already joined");
                    }
                } else {
                    sendMessage("Wrong login or password");
                }
            }
        }
    }

    void closeConnection() {
        serverEngine.remSubscriber(this);
        serverEngine.sendBroadcast(nickName + " has disconnected");
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

    private void readMessage() throws IOException, SQLException {
        while (true) {
            String inputMessage = dis.readUTF();
            System.out.println(nickName + ": " + inputMessage);
            if (inputMessage.startsWith("/")) {
                //clients request
                if (inputMessage.startsWith("/cr")) {
                    serverEngine.onlineClientsList(this);
                }
                //private message
                if (inputMessage.startsWith("/w")) {
                    String[] strings = inputMessage.split("\\s", 3);
                    if (strings.length == 3) {
                        serverEngine.directSend(this, strings[1], strings[2]);
                    } else {
                        sendMessage("Message didn't send, try again");
                    }
                }
                //change nick
                if (inputMessage.startsWith("/cn")) {
                    String[] strings = inputMessage.split("\\s");
                    if (strings.length == 2 && strings[1].matches("\\w*")) {
                        serverEngine.changeNick(this, strings[1]);
                    } else {
                        sendMessage("Nick didn't change, use only letters and digits");
                    }
                }
                //quit
                if (inputMessage.equals("/q")) {
                    sendMessage(nickName + " left the chat");
                    return;
                }
            } else {
                serverEngine.sendBroadcast(nickName + ": " + inputMessage);
            }
        }
    }
}
