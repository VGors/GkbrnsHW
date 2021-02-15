package com.geekbrains.j1.lesson08;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VChat extends JFrame {
    public VChat() {
        setTitle("VChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(300, 300, 400, 572);
        JButton sndButton = new JButton("Send");
        JTextField sndTextField = new JTextField();
        JTextArea rsvTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(rsvTextArea);
        setLayout(null);
        add(scrollPane);
        add(sndTextField);
        add(sndButton);
        scrollPane.setBounds(5, 5, 385, 500);
        sndTextField.setBounds(5, 511, 280, 26);
        sndButton.setBounds(290, 511, 100, 25);

        rsvTextArea.setEditable(false);
        rsvTextArea.setLineWrap(true);
        rsvTextArea.setFont(new Font("Arial", Font.PLAIN, 19));
        sndTextField.setFont(new Font("Arial", Font.PLAIN, 18));

        sndTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionOnClick(rsvTextArea, sndTextField);
            }
        });

        sndButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                actionOnClick(rsvTextArea, sndTextField);
            }

        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                sndTextField.requestFocus();
            }
        });
        setVisible(true);
    }

    private void actionOnClick(JTextArea rsvTextArea, JTextField sndTextField) {
        if (!sndTextField.getText().trim().equals("")){
            rsvTextArea.append(sndTextField.getText() + "\n");
        }
        sndTextField.setText("");
    }
}
