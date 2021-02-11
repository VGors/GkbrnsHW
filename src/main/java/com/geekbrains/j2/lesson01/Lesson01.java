package com.geekbrains.j2.lesson01;

public class Lesson01 {
    private static final Object[] TRIERS = new Object[4];     // Pretenders' array
    private static final Object[] OBSTACLES = new Object[6];  // Obstacles' array

    static {
        TRIERS[0] = new Robot("Terminator", 47, 15);
        TRIERS[1] = new Cat("Baton", 26, 10);
        TRIERS[2] = new Man("Igor", 10, 9);
        TRIERS[3] = new Man("Vardan", 29, 19);
        OBSTACLES[0] = new Wall(2);
        OBSTACLES[1] = new Racetrack(7);
        OBSTACLES[2] = new Wall(7);
        OBSTACLES[3] = new Racetrack(11);
        OBSTACLES[4] = new Wall(3);
        OBSTACLES[5] = new Wall(3);
    }

    public static void main(String[] args) {
        competition();
    }

    private static void competition() {
        for (Object trier : TRIERS) {
            if (trier instanceof Robot) {
                ((Robot) trier).actionSelector(OBSTACLES);
            } else if (trier instanceof Cat) {
                ((Cat) trier).actionSelector(OBSTACLES);
            } else if (trier instanceof Man) {
                ((Man) trier).actionSelector(OBSTACLES);
            } else {
                System.out.println("Unknown object...");
                break;
            }
        }
    }

}
