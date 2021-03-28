package com.geekbrains.j3.lesson_04.task2.server.services;

import com.geekbrains.j3.lesson_04.task2.server.interfaces.Censurable;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class ServerEngine implements Censurable {
    private final int PORT = 8189;
    private List<ClientHandler> clientBase;
    private Connection connection;
    private final String USERSDB = "usersInfo";
    private final File censureFile = new File("cities.txt");
    private Map<String, String> censureMap;

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
            censureFileIni();
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

    public synchronized void onlineClientsList(ClientHandler clientHandler) {
        clientHandler.sendMessage("Online: " + clientBase.toString());
    }

    public synchronized void sendBroadcast(String str) {
        for (ClientHandler ch : clientBase) {
            ch.sendMessage(str);
        }
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

    @Override
    public void censureFileIni() throws IOException {
        censureMap = new HashMap<>();
        if (censureFile.exists() && censureFile.isFile()) {
            Files.lines(censureFile.toPath()).forEach(k -> {
                String[] bufString = k.split("->", 2);
                censureMap.put(bufString[0], bufString[1]);
            });
            System.out.println("Censure block loaded");
        }
    }

    @Override
    public String msgFilter(String msg) {
        StringBuilder censuredMsg = new StringBuilder();
        String[] msgWords = msg.split("\\s");
        Arrays.stream(msgWords).forEach(k -> {
            if (censureMap.containsKey(k.trim())) {
                censuredMsg.append(censureMap.get(k)).append(" ");
            } else {
                censuredMsg.append(k).append(" ");
            }
        });
        return censuredMsg.toString();
    }
}
