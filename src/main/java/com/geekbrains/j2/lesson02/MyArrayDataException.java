package com.geekbrains.j2.lesson02;

public class MyArrayDataException extends NumberFormatException {

    public MyArrayDataException(String message) {
        System.out.println("My \u001B[34mDataArray\u001B[0m exception: " + message);
    }

}
