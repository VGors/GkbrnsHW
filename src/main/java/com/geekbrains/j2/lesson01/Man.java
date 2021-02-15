package com.geekbrains.j2.lesson01;

public class Man implements Moving {
    private final String NAME;
    private int runReserve;
    private int jumpReserve;
    private int countWalls;
    private int countSections;


    public Man(String name, int runReserve, int jumpReserve) {
        this.NAME = name;
        this.runReserve = runReserve;
        this.jumpReserve = jumpReserve;
    }

    @Override
    public boolean run(int distance) {
        if (distance <= runReserve) {
            reduceRunReserve(distance);
            countSections++;
            System.out.printf("\u001B[34mMan %s has run %d section\n\u001B[0m", NAME, countSections);
            return false;
        } else {
            printResult();
            return true;
        }
    }

    @Override
    public boolean jump(int height) {
        if (height <= jumpReserve) {
            reduceJumpReserve(height);
            countWalls++;
            System.out.printf("\u001B[34mMan %s has jumped %d wall\n\u001B[0m", NAME, countWalls);
            return false;
        } else {
            printResult();
            return true;
        }
    }

    @Override
    public void reduceJumpReserve(int quantity) {
        jumpReserve -= quantity;
    }

    @Override
    public void reduceRunReserve(int quantity) {
        runReserve -= quantity;
    }

    @Override
    public void actionSelector(Object[] obstacle) {
        for (int i = 0; i < obstacle.length; i++) {
            if (obstacle[i] instanceof Racetrack) {
                if (run(((Racetrack) obstacle[i]).getLength())) {
                    return;
                }
            } else {
                if (jump(((Wall) obstacle[i]).getHeight())) {
                    return;
                }
            }
            if (i == obstacle.length - 1) {
                System.out.printf("\u001B[32m!Man %s finished!\n\n\u001B[0m", NAME);
            }
        }
    }

    private void printResult() {
        System.out.printf("\u001B[31mMan %s gave up :(\n\u001B[0m", NAME);
        System.out.printf("    Walls over jumped: %d\n", countWalls);
        System.out.printf("    Sections finished: %d\n\n", countSections);
    }
}
