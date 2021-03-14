package com.geekbrains.j2.lesson08.server.interfaces;

public interface AuthService {
    void start();

    void stop();

    String getNickByLogPass(String name, String pswd);
}
