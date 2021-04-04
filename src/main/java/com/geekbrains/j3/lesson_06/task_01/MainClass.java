package com.geekbrains.j3.lesson_06.task_01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {
    static CountDownLatch countDownLatch;
    static CyclicBarrier cyclicBarrier;
    static Semaphore tunnelSemaphore;
    static AtomicInteger place;
    private static final int CARS_COUNT;
    private static final ExecutorService EXECUTOR_SERVICE;
    private static final FutureTask[] CARS_FUTURE_TASK_ARRAY;
    private static final Race RACE;
    private static final Car[] CARS;
    private static final Logger LOGGER;

    static {
        CARS_COUNT = 4;
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(CARS_COUNT);
        CARS_FUTURE_TASK_ARRAY = new FutureTask[CARS_COUNT];
        RACE = new Race(new Road(60), new Tunnel(), new Road(40));
        CARS = new Car[CARS_COUNT];
        cyclicBarrier = new CyclicBarrier(CARS_COUNT);
        countDownLatch = new CountDownLatch(CARS_COUNT);
        tunnelSemaphore = new Semaphore(CARS_COUNT / 2);
        place = new AtomicInteger();
        place.getAndIncrement();
        LOGGER = LogManager.getLogger(MainClass.class);
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        raceManager();
    }

    private static void raceManager() throws InterruptedException {
        LOGGER.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        carsCreation();
        carsStarting();
        countDownLatch.await();
        LOGGER.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        countDownLatch = new CountDownLatch(CARS_COUNT);
        countDownLatch.await();
        LOGGER.info("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        EXECUTOR_SERVICE.shutdown();
    }

    private static void carsCreation() {
        for (int i = 0; i < CARS.length; i++) {
            CARS[i] = new Car(RACE, 20 + (int) (Math.random() * 10));
            CARS_FUTURE_TASK_ARRAY[i] = new FutureTask(CARS[i], true);
        }
    }

    private static void carsStarting() {
        for (int i = 0; i < CARS.length; i++) {
            EXECUTOR_SERVICE.execute(CARS_FUTURE_TASK_ARRAY[i]);
        }
    }
}