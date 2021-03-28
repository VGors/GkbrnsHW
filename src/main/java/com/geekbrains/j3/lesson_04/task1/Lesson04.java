package com.geekbrains.j3.lesson_04.task1;

public class Lesson04 {
    private static final Object mon = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (mon) {
                for (int i = 0; i < 5; i++) {
                    System.out.print("A ");
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mon.notify();
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            synchronized (mon) {
                for (int i = 0; i < 5; i++) {
                    System.out.print("B ");
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mon.notify();
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            synchronized (mon) {
                for (int i = 0; i < 5; i++) {
                    System.out.print("C ");
                    mon.notify();
                    try {
                        mon.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread3.start();
    }
}
