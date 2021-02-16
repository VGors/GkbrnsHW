package com.geekbrains.j2.lesson02;

public class MyArraySizeException extends Throwable {

    public MyArraySizeException(String message) {
        System.out.println("My \u001B[34mArraySize\u001B[0m exception: " + message);
    }
}
