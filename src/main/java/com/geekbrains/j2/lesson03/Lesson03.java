package com.geekbrains.j2.lesson03;

import java.util.*;

public class Lesson03 {

    static List<String> wordArrayList;

    static {
        wordArrayList = new LinkedList<>(Arrays.asList("lorem", "ipsum", "dolor", "sit", "aspernatur",
                "amet", "consectetur", "adipisicing", "autem", "facilis", "iusto", "impedit", "perferendis",
                "elit", "ipsum", "aspernatur", "suscipit", "ipsum", "eveniet"));
    }

    public static void main(String[] args) throws Exception {
        task1();
        task2();
    }

    private static void task1() {
        System.out.println("Task #1:");
        Map<String, Integer> bufMap = new HashMap<>();
        for (String str : wordArrayList) {
            if (bufMap.containsKey(str)) {
                bufMap.put(str, bufMap.get(str) + 1);
            } else {
                bufMap.put(str, 1);
            }
        }

        // Point #1
        System.out.println("\u001B[34mUnique words:\u001B[0m");
        bufMap.forEach((k, j) -> System.out.println(k));

        // Point #2
        System.out.println("\n\u001B[34mNumbers of words:\u001B[0m");
        bufMap.forEach((k, j) -> System.out.println(k + " - " + j));
    }

    private static void task2() {
        System.out.println("\nTask #2:");
        PhoneBook myBook = new PhoneBook();
        myBook.add("Charles Darwin", "+10 11 567 99 00");
        myBook.add("Charles Darwin", "+10 11 567 99 01");
        myBook.add("Charles Darwin", "+10 11 567 99 00");
        myBook.add("Albert Einstein", "+10 11 567 99 03");
        myBook.add("Albert Einstein", "+10 11 567 99 05");
        myBook.add("Nils Bor", "+10 11 567 99 07");
        myBook.add("Alan Turing", "+10 11 567 99 08");

        myBook.get("Charles Darwin");
        myBook.get("Victor Gorshenev");
        myBook.get("Nils Bor");
    }
}
