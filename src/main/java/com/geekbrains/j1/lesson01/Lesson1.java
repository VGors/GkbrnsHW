package com.geekbrains.j1.lesson01;

public class Lesson1 {
    public static void main(String[] args) {
        //Task 2
        byte dogAge = 12;
        short treeAge = 389;
        int backWay = 1280;
        long mySalary = 7800000L;
        float heightOfGrass = 12.4F;
        double piNumber = Math.PI;
        char firstABCLetter = 'A';
        boolean iLikeProgramming = true;
        String myName = "Victor";
    }

    // Task 3
    public static float calculateExpression(float a, float b, float c, float d){
        return a * (b + (c / d));
    }

    // Task 4
    public static boolean isInRange(int n1, int n2){
        int sum = n1 + n2;
        if (sum >= 10 && sum <= 20) {
            return true;
        }
        return false;
    }

    // Task 5
    public static void isPositivePrint(int number){
        if (number < 0) {
            System.out.println("The number is negative");
        }
        System.out.println("The number is positive");
    }

    // Task 6
    public static boolean isPositiveReturn(int number){
        if (number < 0) {
            return true;
        }
        return false;
    }

    // Task 7
    public static void printName (String name){
        System.out.printf("Привет, %s!", name);
    }

    // Task 8
    public static void isLeapYear (int year){
        if (year % 400 == 0){
            System.out.println("Leap-year");
        }else if (year % 100 == 0){
            System.out.println("Not leap-year");
        }else if (year % 4 == 0){
            System.out.println("Leap-year");
        }else {
            System.out.println("Not leap-year");
        }
    }
}
