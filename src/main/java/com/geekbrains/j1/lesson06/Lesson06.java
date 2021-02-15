package com.geekbrains.j1.lesson06;

public class Lesson06 {
    public static void main(String[] args) {

        // Testing zone:
        Cat myFirstCat = new Cat("Baton");
        Cat mySecondCat = new Cat("Vaska");
        Dog myFirstDog = new Dog("Briket");
        Animal myAnimal = new Animal("Corn");

        myFirstCat.swimAnimal(23);
        myFirstCat.runAnimal(34);
        mySecondCat.runAnimal(210);

        myFirstDog.swimAnimal(2);
        myFirstDog.runAnimal(120);

        System.out.printf("Created objects: %d\n", Cat.getObjectsCounter());
    }
}
