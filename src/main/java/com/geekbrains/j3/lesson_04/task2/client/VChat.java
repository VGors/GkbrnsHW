package com.geekbrains.j3.lesson_04.task2.client;

import com.geekbrains.j3.lesson_04.task2.client.interfaces.Logable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class VChat extends JFrame implements Logable {
    private final JTextArea RSV_TEXT_AREA;
    private final JTextField SND_TEXT_FIELD;
    private final String ADDRESS = "localhost";
    private final int IP = 8189;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private File logFile;
    private BufferedWriter logWriter;

    public static void main(String[] args) throws IOException {
        new VChat();
    }

    public VChat() {
        setTitle("VChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(300, 300, 400, 572);
        JButton sndButton = new JButton("Send");
        SND_TEXT_FIELD = new JTextField();
        RSV_TEXT_AREA = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(RSV_TEXT_AREA);
        setLayout(null);
        add(scrollPane);
        add(SND_TEXT_FIELD);
        add(sndButton);
        scrollPane.setBounds(5, 5, 385, 500);
        SND_TEXT_FIELD.setBounds(5, 511, 280, 26);
        sndButton.setBounds(290, 511, 100, 25);
        RSV_TEXT_AREA.setEditable(false);
        RSV_TEXT_AREA.setLineWrap(true);
        RSV_TEXT_AREA.setFont(new Font("Arial", Font.PLAIN, 19));
        SND_TEXT_FIELD.setFont(new Font("Arial", Font.PLAIN, 18));
        SND_TEXT_FIELD.addActionListener(e -> actionOnClick(SND_TEXT_FIELD));
        sndButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                actionOnClick(SND_TEXT_FIELD);
            }

        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SND_TEXT_FIELD.requestFocus();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    out.writeUTF("Client shut down!");
                } catch (IOException ignored) {
                }
                closeConnection();
            }
        });
        chatConnection();
        setVisible(true);
    }

    private void actionOnClick(JTextField sndTextField) {
        if (!sndTextField.getText().trim().equals("")) {
            try {
                out.writeUTF(sndTextField.getText());
            } catch (IOException ignored) {
            }
        }
        sndTextField.setText("");
    }

    private void chatConnection() {
        connectToServer();
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String serverMessage = in.readUTF();
                    if (serverMessage.startsWith("/authok")) {
                        logFileIni(new File(serverMessage.split("\\s")[1] + "_log.txt"));
                        loadFromFile(100);
                        RSV_TEXT_AREA.append(serverMessage + "\n");
                        break;
                    }
                    RSV_TEXT_AREA.append(serverMessage + "\n");
                }
                while (true) {
                    String sentString = in.readUTF();
                    if (sentString.equals("/q")) {
                        out.writeUTF("Client shut down!");
                        closeConnection();
                        logWriter.flush();
                        logWriter.close();
                        return;
                    }
                    logMsgToFile(sentString);
                    RSV_TEXT_AREA.append(sentString + "\n");
                }
            } catch (IOException ignored) {
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void connectToServer() {
        boolean isConnected = false;
        while (!isConnected) {
            try {
                clientSocket = new Socket(ADDRESS, IP);
                isConnected = true;
            } catch (IOException ignored) {
                try {
                    System.out.println("Server is shut down. Please power on the server.");
                    Thread.sleep(2000);
                } catch (InterruptedException ignored1) {
                }
            }
        }
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            in.close();
        } catch (IOException ignored) {
        }
        try {
            out.flush();
        } catch (IOException ignored) {
        }
        try {
            out.close();
        } catch (IOException ignored) {
        }
        try {
            clientSocket.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void logMsgToFile(String msg) {
        try {
            logWriter.write(msg);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(int quantity) {
        try {
            long lines = Files.lines(logFile.toPath()).count();
            if (lines > quantity) {
                Files.lines(logFile.toPath()).skip(lines - quantity).forEach(k -> RSV_TEXT_AREA.append(k + "\n"));
                return;
            }
            Files.lines(logFile.toPath()).forEach(k -> RSV_TEXT_AREA.append(k + "\n"));
        } catch (IOException e) {
            RSV_TEXT_AREA.append("Can't read LOG file");
        }
    }

    @Override
    public void logFileIni(File file) throws IOException {
        if (!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                RSV_TEXT_AREA.append("Can't create log file\n");
                return;
            }
        }
        logFile = file;
        logWriter = new BufferedWriter(new FileWriter(logFile, true));
    }
}
