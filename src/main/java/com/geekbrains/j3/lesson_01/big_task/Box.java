package com.geekbrains.j3.lesson_01.big_task;

import java.util.ArrayList;

public class Box<T extends Fruit> implements Comparable<Box> {
    private ArrayList<T> fruits;        // c
    private T boxType;

    public Box(T boxType) {
        this.boxType = boxType;
        fruits = new ArrayList<>();
    }

    // d
    public float getWeight() {
        float weight = 0;
        if (fruits.size() != 0) {
            for (T k : fruits) {
                weight += k.getWEIGHT();
            }
        }
        return weight;
    }

    // e
    public boolean compare(Box otherBox) {
        return this.getWeight() == otherBox.getWeight();
    }

    // f
    public void fruitTransfer(Box destinationBox) {
        if (this.compareTo(destinationBox) == 1) {
            if (fruits.size() > 0) {
                destinationBox.addFruit(fruits.size());
                fruits.clear();
                return;
            }
            System.out.println("Nothing to transfer");
        } else {
            System.out.println("Different boxes types");
        }
    }

    // g
    public void addFruit(int quantity) {
        if (quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                fruits.add(boxType);
            }
            return;
        }
        System.out.println("Wrong quantity");
    }

    public T getBoxType() {
        return boxType;
    }

    @Override
    public int compareTo(Box otherBox) {
        if (this.getBoxType().toString().equals(otherBox.getBoxType().toString())) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return boxType.getClass().getSimpleName();
    }
}
