package com.geekbrains.j2.lesson08.server.services;

import com.geekbrains.j2.lesson08.server.interfaces.AuthService;

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

    public ServerEngine() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            aService = new AuthServiceImpl();
            aService.start();
            clientBase = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                new CheckConnection(new ClientHandler(this, socket)).start();
                System.out.println("Client connected "
                        + socket.getInetAddress()
                        + ":"
                        + socket.getPort()
                        + " - countdown started...");
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

    public synchronized void onlineClientsList(ClientHandler clientHandler) {
        clientHandler.sendMessage("Online: " + clientBase.toString());
    }

    public synchronized void directSend(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler ch : clientBase) {
            System.out.println(ch.getName());
            if (ch.getName().equals(nickTo)) {
                ch.sendMessage("from " + from.getName() + ": " + msg);
                from.sendMessage("to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMessage("There isn't this client...");
    }

    public synchronized void addSubscriber(ClientHandler ch) {
        clientBase.add(ch);
    }

    public synchronized void remSubscriber(ClientHandler ch) {
        clientBase.remove(ch);
    }
}
