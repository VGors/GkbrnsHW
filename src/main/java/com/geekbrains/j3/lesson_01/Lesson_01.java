package com.geekbrains.j3.lesson_01;

import com.geekbrains.j3.lesson_01.big_task.Apple;
import com.geekbrains.j3.lesson_01.big_task.Box;
import com.geekbrains.j3.lesson_01.big_task.Orange;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;

public class Lesson_01 {
    public static void main(String[] args) {
        testMethod();
    }

    // Task #1
    public static <T> T[] changeElements(T[] sourceArray, int firstElement, int secondElement) {
        try {       // over shoes over boots ))
            T buff = sourceArray[firstElement];
            sourceArray[firstElement] = sourceArray[secondElement];
            sourceArray[secondElement] = buff;
            return sourceArray;
        } catch (Exception e) {
            System.out.println("Something wrong");
            return null;
        }
    }

    // Task #2
    public static <T> ArrayList convertToArrayList(T[] sourceArray) {
        ArrayList<T> newArrayList = new ArrayList<>();
        try {
            for (T t : sourceArray) {
                newArrayList.add(t);
            }
        } catch (Exception e) {
            System.out.println("Wrong source array...");
        }
        return newArrayList;
    }

    private static void testMethod() {
        //task #1
        Integer[] myInteger = {7, 8, 0, 11};
        Double[] myDouble = {7.0, 8.8, 0.3, 11.1};
        String[] myString = {"one", "two", "three", "four", "five"};
        System.out.println(Arrays.toString(myInteger));
        System.out.println(Arrays.toString(changeElements(myInteger, 1, 2)) + "\n");
        System.out.println(Arrays.toString(myDouble));
        System.out.println(Arrays.toString(changeElements(myDouble, 0, 2)) + "\n");
        System.out.println(Arrays.toString(myString));
        System.out.println(Arrays.toString(changeElements(myString, 1, 3)) + "\n");

        //task #2
        // List<String> arrayListStrError = myString;    - error
        List<String> arrayListStr = new ArrayList<>(convertToArrayList(myString));
        List<Integer> arrayListInt = new ArrayList<>(convertToArrayList(myInteger));
        List<Double> arrayListDbl = new ArrayList<>(convertToArrayList(myDouble));

        //BIG task
        Box<Apple> appleBox1 = new Box<>(new Apple());
        Box<Apple> appleBox2 = new Box<>(new Apple());
        Box<Apple> appleBox3 = new Box<>(new Apple());
        Box<Orange> orangeBox1 = new Box<>(new Orange());
        Box<Orange> orangeBox2 = new Box<>(new Orange());
        Box<Orange> orangeBox3 = new Box<>(new Orange());

        appleBox1.addFruit(6);
        appleBox2.addFruit(14);
        appleBox3.addFruit(42);
        orangeBox1.addFruit(7);
        orangeBox2.addFruit(3);
        orangeBox3.addFruit(4);

        System.out.println("appleBox1: " + appleBox1.getWeight());
        System.out.println("appleBox2: " + appleBox2.getWeight());
        System.out.println("appleBox3: " + appleBox3.getWeight());
        System.out.println("orangeBox1: " + orangeBox1.getWeight());
        System.out.println("orangeBox2: " + orangeBox2.getWeight());
        System.out.println("orangeBox3: " + orangeBox3.getWeight() + "\n");

        System.out.println(orangeBox3.compare(appleBox1) + "\n");
        System.out.println(appleBox1.compare(appleBox2) + "\n");

        appleBox1.fruitTransfer(appleBox2);
        System.out.println("appleBox1: " + appleBox1.getWeight());
        System.out.println("appleBox2: " + appleBox2.getWeight());

        appleBox1.fruitTransfer(orangeBox2);
        appleBox1.fruitTransfer(appleBox2);
        System.out.println(appleBox1.getWeight());

    }
}
