package com.geekbrains.j2.lesson03;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private final Map<String, String> PHONE_NAME = new HashMap<>();

    public void add (String name, String phoneNumber){
        if (!PHONE_NAME.containsKey(phoneNumber)){
            PHONE_NAME.put(phoneNumber, name);
            System.out.println("\u001B[32mThe number added successful.\u001B[0m");
        } else {
            System.out.println("\u001B[33mThis number already exist.\u001B[0m");
        }
    }

    public void get (String name){
        List<String> dataList = new LinkedList<>();
        PHONE_NAME.forEach((k, j) -> {
            if (j.equals(name)){
                dataList.add(k);
            }
        });
        System.out.println("\n\u001B[34m" + name + ": \u001B[0m");
        if (dataList.isEmpty()) {
            System.out.println("\u001B[33mNo available numbers.\u001B[0m");
        } else {
            dataList.forEach(System.out::println);
        }
    }

}
