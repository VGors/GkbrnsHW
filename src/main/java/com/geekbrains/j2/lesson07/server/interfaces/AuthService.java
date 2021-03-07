package com.geekbrains.j2.lesson07.server.interfaces;

public interface AuthService {
    void start();

    void stop();

    String getNickByLogPass(String name, String pswd);
}
