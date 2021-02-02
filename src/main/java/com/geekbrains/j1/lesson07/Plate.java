package com.geekbrains.j1.lesson07;

public class Plate {
    private int food;

    public Plate(int food) {
        this.food = food;
    }

    //    Add food to plate - Task 6
    public void addFood(int quantity) {
        if (quantity > 0) {
            this.food += quantity;
            System.out.printf("\u001B[34mINFO:\u001B[0m %d of food added.\n", quantity);
        } else {
            System.out.println("\u001B[34mINFO:\u001B[0m There are no food to add.");
        }
    }

    //    Decrease food from plate
    public boolean eatFood(int quantity, Cat eatingCat) {
        if (quantity > food) {
            System.out.printf("Cat \u001B[31m%s\u001B[0m can't eat - not enough food =(\n", eatingCat.getName());
            return false;
        }
        else {
            food -= quantity;
            System.out.printf("Cat \u001B[32m%s\u001B[0m have ate successful =)\n", eatingCat.getName());
            return true;
        }
    }

    //    Information about plate
    public void plateInfo() {
        System.out.printf("\u001B[34mINFO:\u001B[0m Quantity of food in the plate: %d\n", food);
    }

    public int getFood() {
        return food;
    }
}
