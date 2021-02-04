package com.geekbrains.j1.lesson07;

public class Lesson07 {
    public static Cat[] myCats;
    public static Plate myLargePlate;

    static {
        myCats = new Cat[3];
        myLargePlate = new Plate(27);
        myCats[0] = new Cat("Baton", 17);
        myCats[1] = new Cat("Croissant", 8);
        myCats[2] = new Cat("Bagel", 23);
    }

    public static void main(String[] args) {
        testingMethod();
    }

    private static void testingMethod() {
        myLargePlate.plateInfo();
        myCats[0].eat(myLargePlate);        //Feeding the first cat
        myCats[0].eat(myLargePlate);        //Trying to feed the same cat again
        myLargePlate.plateInfo();

        myCats[1].eat(myLargePlate);        //Feeding the second cat
        myLargePlate.plateInfo();

        myCats[2].eat(myLargePlate);        //Feeding the third cat
        myLargePlate.plateInfo();
        myLargePlate.addFood(25);   //Add some food to plate
        myLargePlate.plateInfo();
//        myCats[2].eat(myLargePlate);        //Trying to feed third cat again
        informationAboutCats(myCats);

    }

    private static void informationAboutCats(Cat[] cats) {
        System.out.printf("Cats - %d:\n", cats.length);
        for (int i = 0; i < cats.length; i++) {
            if (cats[i].isSatiety()) {
                System.out.printf("Cat %s is not hungry\n", cats[i].getName());
            } else {
                System.out.printf("Cat %s is hungry\n", cats[i].getName());
            }
        }
    }
}
