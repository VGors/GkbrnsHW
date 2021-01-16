package com.geekbrains.j1.lesson02;

import java.lang.reflect.Array;
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

    //Task 7
    public static void moveArray(int[] inputArray, int n){

    }
}
