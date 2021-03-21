package com.geekbrains.j3.lesson_01.big_task;

public class Apple extends Fruit {
    private final float WEIGHT = 1f;

    @Override
    public float getWEIGHT() {
        return WEIGHT;
    }

    @Override
    public String toString() {
        return "Apple";
    }
}
