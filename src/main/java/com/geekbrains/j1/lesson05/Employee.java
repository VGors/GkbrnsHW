package com.geekbrains.j1.lesson05;

public class Employee {
    private String fio;
    private String post;
    private String email;
    private String phone;
    private double salary;
    private int age;

    public Employee(String fio, String post, String email, String phone, double salary, int age) {
        this.fio = fio;
        this.post = post;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void getDetailInfo(){
        System.out.printf("\n%s\n", this.fio);
        System.out.printf("post:   %s\n", this.post);
        System.out.printf("e-mail: %s\n", this.email);
        System.out.printf("phone:  %s\n", this.phone);
        System.out.printf("salary: %f$\n", this.salary);
        System.out.printf("age:    %d\n", this.age);
    }
}
