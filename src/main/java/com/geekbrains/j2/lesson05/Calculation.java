package com.geekbrains.j2.lesson05;

public class Calculation implements Runnable {
    public float[] array;

    public Calculation(float[] array) {
        this.array = array;
    }

    public float[] getArray() {
        return array;
    }

    @Override
    public void run() {
        long a = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        a = System.currentTimeMillis() - a;
        System.out.printf("Calculation time: %d\n", a);
    }
}
