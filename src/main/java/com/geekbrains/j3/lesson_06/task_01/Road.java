package com.geekbrains.j3.lesson_06.task_01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Road extends Stage {
    public static final Logger LOGGER;
    static {
        LOGGER = LogManager.getLogger(Road.class);
    }
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            LOGGER.warn(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            LOGGER.info(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
