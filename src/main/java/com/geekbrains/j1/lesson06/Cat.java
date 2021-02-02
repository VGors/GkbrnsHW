package com.geekbrains.j1.lesson06;

public class Cat extends Animal{

    private int maxRunDistance;

    public Cat(String name) {
        super(name);
        this.maxRunDistance = 200;
    }

    public void setMaxRunDistance(int maxRunDistance) {
        this.maxRunDistance = maxRunDistance;
    }

    @Override
    public void runAnimal(int length) {
        if (length <= maxRunDistance){
            System.out.printf("Cat %s ran %d meters\n", getName(), length);
        } else {
            System.out.println("Wrong distance.");
        }
    }

    @Override
    public void swimAnimal(int length) {
        System.out.printf("Cat %s can't swim!\n", getName());
    }
}
