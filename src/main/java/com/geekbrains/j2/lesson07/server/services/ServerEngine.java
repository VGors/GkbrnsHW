package com.geekbrains.j2.lesson07.server.services;

import com.geekbrains.j2.lesson07.server.interfaces.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerEngine {
    private final int PORT = 8189;
    private List<ClientHandler> clientBase;
    private AuthService aService;

    public AuthService getAService() {
        return this.aService;
    }

    ;

    public ServerEngine() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            aService = new AuthServiceImpl();
            aService.start();
            clientBase = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected " + socket.getInetAddress() + ":" + socket.getPort());
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Server error!");
        } finally {
            if (aService != null) {
                aService.stop();
            }
        }
    }

    public synchronized boolean isBusy(String nick) {
        return clientBase.stream().anyMatch(k -> k.getName().equals(nick));
    }

    public synchronized void sendBroadcast(String str) {
        for (ClientHandler ch : clientBase) {
            ch.sendMessage(str);
        }
    }

    public synchronized boolean directSend(String nick, String str) {
        for (ClientHandler ch : clientBase) {
            System.out.println(ch.getName());
            if (ch.getName().equals(nick)){
                ch.sendMessage(str);
                return true;
            }
        }
        return false;
    }


    public synchronized void addSubscriber(ClientHandler ch) {
        clientBase.add(ch);
    }

    public synchronized void remSubscriber(ClientHandler ch) {
        clientBase.remove(ch);
    }


}
