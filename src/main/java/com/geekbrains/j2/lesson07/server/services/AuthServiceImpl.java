package com.geekbrains.j2.lesson07.server.services;

import com.geekbrains.j2.lesson07.server.interfaces.AuthService;

import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    private List<User> usersList;

    public AuthServiceImpl() {
        usersList = new ArrayList<>();
        usersList.add(new User("A", "123", "An"));
        usersList.add(new User("B", "123", "Bn"));
        usersList.add(new User("C", "123", "Cn"));
    }

    @Override
    public void start() {
        System.out.println("Start");
    }

    @Override
    public void stop() {
        System.out.println("Stop");
    }

    @Override
    public String getNickByLogPass(String login, String password) {
        for (User u : usersList) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.nick;
            }
        }
        return "";
    }

    public List<User> getUsersList() {
        return usersList;
    }

    private class User {
        private String login;
        private String password;
        private String nick;

        public User(String name, String password, String nick) {
            this.login = name;
            this.password = password;
            this.nick = nick;
        }
    }
}
