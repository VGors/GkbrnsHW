package com.geekbrains.j2.lesson07.client.one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VChat extends JFrame {
    private final JTextArea RSV_TEXT_AREA;
    private final JTextField SND_TEXT_FIELD;
    private final String ADDRESS = "localhost";
    private final int IP = 8189;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isAuthorised;

    public static void main(String[] args) throws IOException {
        new VChat();
    }

    public VChat() throws IOException {
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

    public boolean isAuthorised() {
        return isAuthorised;
    }

    public void setAuthorised(boolean authorised) {
        isAuthorised = authorised;
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
        setAuthorised(false);
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String serverMessage = in.readUTF();
                    if (serverMessage.startsWith("/authok")){
                        setAuthorised(true);
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
                        return;
                    }
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

}
