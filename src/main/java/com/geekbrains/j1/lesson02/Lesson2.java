package com.geekbrains.j1.lesson02;

import java.util.Arrays;

public class Lesson2 {
    public static void main (String[] args){
        //Task 1
        int counter = 0;
        byte[] bitArray = {0, 0, 1, 0, 1, 1, 1, 0};
        for (byte i: bitArray) {
            if (i == 0){
                bitArray[counter] = 1;
            }
            else {
                bitArray[counter] = 0;
            }
            counter++;
        }

        //Task 2
        int[] emptyArray = new int[8];
        for (int i = 0; i < emptyArray.length; i++) {
            emptyArray[i] = i * 3;
        }

        //Task 3
        byte[] thirdArray = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < thirdArray.length; i++) {
            if (thirdArray[i] < 6){
                thirdArray[i] *= 2;
            }
        }

        //Task 4
        int[][] squareArray = new int[8][8];
        for (int i = 0; i < squareArray.length; i++) {
            squareArray[i][i] = 1;
            squareArray[i][squareArray.length - i - 1] = 1;
        }

        //Task 5
        int[] randomArray = {8, 7, 3, 9, 12, 78, 4, 15};
        System.out.println(Arrays.stream(randomArray).min().getAsInt());
        System.out.println(Arrays.stream(randomArray).max().getAsInt());

        //testing zone for task 7:
        int[] testArray = {0, 1, 2, 3, 4};
        moveArray(testArray, -2);
    }

    //Task 6
    public static boolean chekBalance(int[] inputArray){
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            // Суммируется левая часть массива:
            for (int j = 0; j < i + 1; j++) {
                sum1 += inputArray[j];
            }
            // Суммируется правая часть массива:
            for (int j = i + 1; j < inputArray.length; j++) {
                sum2 += inputArray[j];
            }
            // Проверка на равенство:
            if (sum1 == sum2){
                return true;
            }
            sum1 = sum2 = 0;
        }
        return false;
    }

    //Task 7 (If positive n - shift right)
    public static void moveArray(int[] inputArray, int n){
        // Checking empty shift and one element array
        if (n == 0 || inputArray.length == 1) {
            System.out.println("Nothing to do...");
            return;
        }

        System.out.printf("Original array: %s\n", Arrays.toString(inputArray));
        int buf;    //storing template element

        // Negative shift
        if (n < 0){
            for (int k = 1; k <= Math.abs(n); k++) {
                for (int i = 1; i < inputArray.length; i++) {
                    buf = inputArray[i];
                    if (i != inputArray.length - 1){
                        inputArray[i] = inputArray[i + 1];
                        inputArray[i + 1] = buf;
                    }
                    else {
                        inputArray[i] = inputArray[0];
                        inputArray[0] = buf;
                    }
                }
            }
        }

        // Positive shift
        else {
            for (int k = 1; k <= Math.abs(n); k++) {
                for (int i = inputArray.length - 2; i >= 0; i--) {
                    buf = inputArray[i];
                    if (i != 0) {
                        inputArray[i] = inputArray[i - 1];
                        inputArray[i - 1] = buf;
                    } else {
                        inputArray[i] = inputArray[inputArray.length - 1];
                        inputArray[inputArray.length - 1] = buf;
                    }
                }
            }
        }
        System.out.printf("   Final array: %s\n", Arrays.toString(inputArray));
    }
}
