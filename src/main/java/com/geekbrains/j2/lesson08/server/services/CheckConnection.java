package com.geekbrains.j2.lesson08.server.services;

public class CheckConnection extends Thread {
    private ClientHandler clientHandler;
    private final int SECTODISCONNECT = 120;

    public CheckConnection(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(SECTODISCONNECT * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (clientHandler.getName().isEmpty()) {
            System.out.print(clientHandler.getSocket().getInetAddress() + ":"
                    + clientHandler.getSocket().getPort() + " ");
            clientHandler.closeConnection();
        } else {
            System.out.println(clientHandler.getSocket().getInetAddress() + ":"
                    + clientHandler.getSocket().getPort() + " is ok");
        }
    }
}
