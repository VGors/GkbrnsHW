package com.geekbrains.j3.lesson_02.server.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerEngine {
    private final int PORT = 8189;
    private List<ClientHandler> clientBase;
    private Connection connection;
    private final String USERSDB = "usersInfo";

    public ServerEngine() throws IOException {
        try {
            connection = getConnection();
            System.out.println("Successful Connecting to users DB");
        } catch (SQLException e) {
            System.out.println("SQL connection error");
            return;
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
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
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("I can't close connection!");
            }
        }
    }

    public synchronized boolean isBusy(String nick) {
        return clientBase.stream().anyMatch(k -> k.getNickName().equals(nick));
    }

    public synchronized boolean isExist(String nick) {
        try (Statement statement = connection.createStatement()) {
            String sqlString = "select nickName from " + USERSDB + " where nickName = '" + nick + "'";
            ResultSet resultSet = statement.executeQuery(sqlString);
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("SQL Error");
            return false;
        }
    }

    public String getNickByLogPass(String name, String password) {
        try (Statement statement = connection.createStatement()) {
            String sqlString = "select nickName from " + USERSDB + " where name = '"
                    + name + "' and password = '"
                    + password + "'";
            ResultSet resultSet = statement.executeQuery(sqlString);
            if (resultSet.next()) {
                return resultSet.getString("nickName");
            }
            return "";
        } catch (SQLException e) {
            System.out.println("SQL Error");
            return "";
        }
    }

    public void changeNick(ClientHandler clientHandler, String newNick) throws SQLException {
        String oldNick = clientHandler.getNickName();
        try (Statement statement = connection.createStatement()) {
            String sql = "update " + USERSDB + " set nickName = '"
                    + newNick + "' where nickName = '"
                    + oldNick + "'";
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            clientHandler.sendMessage("SQL error - nick didn't change");
            return;
        } finally {
            connection.setAutoCommit(true);
        }
        clientBase.stream().anyMatch(k -> {
            if (k.getNickName().equals(oldNick)) {
                k.setNickName(newNick);
                clientHandler.sendMessage("Nick have changed successful");
            }
            return false;
        });
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
            if (ch.getNickName().equals(nickTo)) {
                ch.sendMessage("from " + from.getNickName() + ": " + msg);
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

    private Connection getConnection() throws SQLException {
        classIni();
        return DriverManager.getConnection("jdbc:sqlite:users.s3db");
    }

    private void classIni() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
    }
}
