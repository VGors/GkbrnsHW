package com.geekbrains.j2.lesson08.server;

import com.geekbrains.j2.lesson08.server.services.ServerEngine;


public class ServerApp {
    public static void main(String[] args) {
        Object object = new Integer(10);
        String string = (String) object;
        System.out.println(string);
//        new ServerEngine();
    }
}
