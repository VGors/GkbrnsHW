package com.geekbrains.j2.lesson02;

public class Lesson02 {
    private static final String[][] MY_ARRAY;

    static {
        MY_ARRAY = new String[4][];
        MY_ARRAY[0] = new String[]{"5", "7", "18", "8"};
        MY_ARRAY[1] = new String[]{"7", "19", "7", "16"};
        MY_ARRAY[2] = new String[]{"11", "t", "0", "45"};
        MY_ARRAY[3] = new String[]{"0", "3", "15", "40"};
    }

    public static void main(String[] args) {
        try {
            System.out.println("Result is: " + pullArray(MY_ARRAY));
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("Caught exception is: " + e);
        }
    }

    private static int pullArray(String[][] sourceArray) throws MyArraySizeException, MyArrayDataException {
        int tmpRLength = sourceArray.length;
        if (tmpRLength != 4) {
            throw new MyArraySizeException("Wrong number of rows, expected 4 instead " + tmpRLength);
        }
        for (String[] a : sourceArray) {
            if (a.length != 4) {
                throw new MyArraySizeException("Wrong size of row, expected 4 instead " + a.length);
            }
        }
        int sum = 0;
        for (int i = 0; i < tmpRLength; i++) {
            int tmpCLength = sourceArray[i].length;
            for (int j = 0; j < tmpCLength; j++) {
                try {
                    sum += Integer.parseInt(sourceArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Wrong symbol in the cell [" + i + ", " + j + "]");
                }
            }
        }
        return sum;
    }
}
