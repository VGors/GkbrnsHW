package com.geekbrains.j3.lesson_06.task_01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tunnel extends Stage {
    public static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(Tunnel.class);
    }

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            LOGGER.warn(c.getName() + " готовится к этапу(ждет): " + description);
            MainClass.tunnelSemaphore.acquire();
            LOGGER.info(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        } finally {
            LOGGER.info(c.getName() + " закончил этап: " + description);
            MainClass.tunnelSemaphore.release();
        }
    }
}
