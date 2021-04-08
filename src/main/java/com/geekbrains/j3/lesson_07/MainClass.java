package com.geekbrains.j3.lesson_07;

import com.geekbrains.j3.lesson_07.services.TestingClass;

public class MainClass {
    public static void main(String[] args) {
        Class classForTests = new TestClass().getClass();
        TestingClass.start(classForTests);
    }
}
