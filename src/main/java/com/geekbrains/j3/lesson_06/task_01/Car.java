package com.geekbrains.j3.lesson_06.task_01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static final Logger LOGGER;

    static {
        CARS_COUNT = 0;
        LOGGER = LogManager.getLogger(Car.class);
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
            LOGGER.warn(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            carIsReady = true;
            MainClass.cyclicBarrier.await();
            LOGGER.info(this.name + " готов");
            MainClass.countDownLatch.countDown();
            MainClass.cyclicBarrier.await();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        LOGGER.info(this.name + " have finished! - " + MainClass.place.getAndIncrement() + " место.");
        MainClass.countDownLatch.countDown();
    }
}
