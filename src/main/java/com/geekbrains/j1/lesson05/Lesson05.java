package com.geekbrains.j1.lesson05;

public class Lesson05 {
    public static void main(String[] args) {
        Employee[] team = new Employee[5];
        team[0] = new Employee("Y Zucchini", "SEO", "boss@mail.uk", "+10 555-77-11", 7000.99, 40);
        team[1] = new Employee("D Johnson", "1-st assistant", "first@mail.uk", "+10 555-77-22", 6000, 57);
        team[2] = new Employee("Lil Jack", "2-nd assistant", "second@mail.uk", "+10 555-77-33", 5800, 45);
        team[3] = new Employee("Someone", "Secretary", "secretary@mail.uk", "+10 555-73-45", 700.99, 24);
        team[4] = new Employee("Mr. Piterson", "none", "none@mail.uk", "+10 555-11-11", 0.99, 32);
        for (int i = 0; i < team.length; i++) {
            if (team[i].getAge() > 40) {
                team[i].getDetailInfo();
            }
        }
    }

}
