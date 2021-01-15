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


        //Task 4

        //Task 5
    }

    //Task 6
    public static boolean chekBalance(int[] inputArray){
        return true;
    }

    //Task 7
    public static void moveArray(int[] inputArray, int n){

    }
}
