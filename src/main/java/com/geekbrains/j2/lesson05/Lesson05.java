package com.geekbrains.j2.lesson05;

import java.util.Arrays;

public class Lesson05 {

    static final int SIZE = 10000000;  // 000
    static final int H = SIZE / 2;
    static final float[] ARR = new float[SIZE];
    static final float[] ARR_1 = new float[H];
    static final float[] ARR_2 = new float[H];


    public static void main(String[] args) throws InterruptedException {
        method1();
        method2();
    }

    private static void method1() {
        Arrays.fill(ARR, 1);
        System.out.println("Method #1 (wait please):");
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            ARR[i] = (float) (ARR[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        a = System.currentTimeMillis() - a;
        System.out.printf("Calculation time: %d\n", a);
    }

    private static void method2() throws InterruptedException {
        Arrays.fill(ARR, 1);
        System.out.println("\nMethod #2 (wait please):");

        // Stage 1 - dividing
        long a = System.currentTimeMillis();
        System.arraycopy(ARR, 0, ARR_1, 0, H);
        System.arraycopy(ARR, H, ARR_2, 0, H);
        a = System.currentTimeMillis() - a;
        System.out.printf("Dividing time: %d\n", a);

        // Stage 2 - calculating
        Calculation calculation1 = new Calculation(ARR_1);
        Calculation calculation2 = new Calculation(ARR_2);
        Thread thread1 = new Thread(calculation1);
        Thread thread2 = new Thread(calculation2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        //Stage 3 - sticking
        a = System.currentTimeMillis();
        System.arraycopy(calculation1.getArray(), 0, ARR, 0, H);
        System.arraycopy(calculation2.getArray(), 0, ARR, H, H);
        a = System.currentTimeMillis() - a;
        System.out.printf("Sticking time: %d\n", a);
    }

}
