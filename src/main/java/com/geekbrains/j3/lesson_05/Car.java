package com.geekbrains.j3.lesson_05;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private boolean carIsReady = false;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isReady() {
        return carIsReady;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            carIsReady = true;
            MainClass.cyclicBarrier.await();
            System.out.println(this.name + " готов");
            MainClass.countDownLatch.countDown();
            MainClass.cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        System.out.println(this.name + " have finished! - " + MainClass.place.getAndIncrement() + " место.");
        MainClass.countDownLatch.countDown();
    }
}
