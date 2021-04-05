package com.geekbrains.j3.lesson_06.task_02_03;

import java.util.Arrays;

public class MainClass {
    public int[] beyondLastFour(int[] input) {
        for (int i = input.length - 1; i >= 0; i--) {
            if (input[i] == 4) {
                return Arrays.copyOfRange(input, i + 1, input.length);
            }
        }
        throw new RuntimeException("Runtime Exception!");
    }

    public boolean containOneOrFour(int[] input) {
        Arrays.sort(input);
        return Arrays.binarySearch(input, 1) >= 0 & Arrays.binarySearch(input, 4) >= 0;
    }
}
