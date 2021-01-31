package com.geekbrains.j1.lesson06;

public class Animal {

    private String name;
    private static int objectsCounter;

    public Animal(String name) {
        objectsCounter++;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int getObjectsCounter() {
        return objectsCounter;
    }

    public void runAnimal(int length){
        System.out.printf("Animal %s ran %d meters\n", name, length);
    }

    public void swimAnimal(int length){
        System.out.printf("Animal %s swam %d meters\n", name, length);
    }

}
