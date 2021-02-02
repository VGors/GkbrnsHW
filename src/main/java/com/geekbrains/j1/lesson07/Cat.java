package com.geekbrains.j1.lesson07;

public class Cat {
    private String name;
    private int appetite;
    private boolean satiety = false;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    //   Cat eats
    public void eat(Plate plate) {
        if (!satiety) {
            if (plate.eatFood(appetite, this)) {
                satiety = true;
            }
        } else {
            System.out.printf("Cat %s is not hungry.\n", name);
        }
    }

    //   Return cat's name
    public String getName() {
        return name;
    }

    public boolean isSatiety() {
        return satiety;
    }
}
