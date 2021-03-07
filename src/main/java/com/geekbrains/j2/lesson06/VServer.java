package com.geekbrains.j2.lesson06;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class VServer {
    private static String consStr = "";
    private static Socket socket = null;
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static BufferedReader reader;
    private static final int PORT = 8189;

    public static void main(String[] args) {
        createConnection();
    }

    private static void createConnection() {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Server is waiting for client...");
            socket = ss.accept();
            System.out.println("Client connected!");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(() -> {
                reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        consStr = reader.readLine();
                    } catch (IOException ignored) {
                    }
                    if (consStr.equalsIgnoreCase("/q")) {
                        try {
                            reader.close();
                            dos.writeUTF("Server shut down!");
                            closeConnection();
                        } catch (IOException ignored) {
                        }
                        break;
                    }
                    if (!consStr.trim().equals("")) {
                        try {
                            dos.writeUTF(consStr);
                            consStr = "";
                        } catch (IOException ignored) {
                        }
                    }
                }
            });
            readThread.setDaemon(true);
            readThread.start();
            while (true) {
                String bufStr = dis.readUTF();
                if (bufStr.equalsIgnoreCase("/q")) {
                    closeConnection();
                    break;
                }
                System.out.println(bufStr);
                dos.writeUTF(bufStr);
            }
        } catch (IOException ignored) {
        }
    }

    private static void closeConnection() {
        System.out.println("Server shutting down...");
        try {
            dos.flush();
        } catch (IOException ignored) {
        }
        try {
            dos.close();
        } catch (IOException ignored) {
        }
        try {
            dis.close();
        } catch (IOException ignored) {
        }
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
