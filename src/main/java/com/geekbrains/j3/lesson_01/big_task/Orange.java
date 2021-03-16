package com.geekbrains.j3.lesson_01.big_task;

public class Orange extends Fruit {
    private final float WEIGHT = 1.5f;

    @Override
    public float getWEIGHT() {
        return WEIGHT;
    }

    @Override
    public String toString() {
        return "Orange";
    }
}
