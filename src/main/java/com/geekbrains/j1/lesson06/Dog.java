package com.geekbrains.j1.lesson06;

public class Dog extends Animal{
    private int maxRunDistance;
    private int maxSwimDistance;

    public Dog(String name) {
        super(name);
        this.maxRunDistance = 500;
        this.maxSwimDistance = 10;
    }

    public void setMaxRunDistance(int maxRunDistance) {
        this.maxRunDistance = maxRunDistance;
    }

    public void setMaxSwimDistance(int maxSwimDistance) {
        this.maxSwimDistance = maxSwimDistance;
    }

    @Override
    public void runAnimal(int length) {
        if (length <= maxRunDistance){
            System.out.printf("Dog %s ran %d meters\n", getName(), length);
        } else {
            System.out.println("Wrong distance.");
        }
    }

    @Override
    public void swimAnimal(int length) {
        if (length <= maxSwimDistance) {
            System.out.printf("Dog %s swam %d meters\n", getName(), length);
        } else {
            System.out.println("Wrong distance.");
        }
    }
}
