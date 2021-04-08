package com.geekbrains.j3.lesson_07;

import com.geekbrains.j3.lesson_07.annotations.*;

public class TestClass {
    @BeforeSuite
    public void beforeMethod() {
        System.out.println("I'm BEFORE method.");
    }

    @Test(value = 7)
    public void firstTestMethod() {
        System.out.println("I'm FIRST TEST method");
    }

    @Test(value = 7)
    public void secondTestMethod() {
        System.out.println("I'm SECOND TEST method");
    }
    @Test(value = 8)
    public void thirdTestMethod() {
        System.out.println("I'm THIRD TEST method");
    }

    @Test(value = 2)
    public void higherPriorityMethod() {
        System.out.println("I'm HIGHER PRIORITY method");
    }

    @AfterSuite
    public void afterMethod() {
        System.out.println("I'm AFTER method");
    }
}
