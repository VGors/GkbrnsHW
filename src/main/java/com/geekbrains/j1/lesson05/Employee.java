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
        System.out.printf("\n%s\n", fio);
        System.out.printf("post:   %s\n", post);
        System.out.printf("e-mail: %s\n", email);
        System.out.printf("phone:  %s\n", phone);
        System.out.printf("salary: %f$\n", salary);
        System.out.printf("age:    %d\n", age);
    }
}
