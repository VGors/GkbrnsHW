package com.geekbrains.j2.lesson04;

import com.geekbrains.j2.lesson04.interfaces.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Lesson04 {
    private static final Integer[] INTEGER_ARRAY;
    private static final List<Integer> INTEGER_LIST;
    private static final List<String> STRING_LIST;
    private static final String TEST_STRING;

    static {
        INTEGER_ARRAY = new Integer[]{5, 7, 3, 4, 0, -4};
        INTEGER_LIST = Arrays.asList(56, 23, 12, 200, 5);
        STRING_LIST = Arrays.asList("one", "and", "apple", "aim", "two", "abc");
        TEST_STRING = "abc is an alphabet";
    }

    public static void main(String[] args) {
        testing();
    }

    private static void testing() {
        IntSearchable searchInt = (k, lst) -> IntStream
                .range(0, lst.length)
                .filter(i -> lst[i] == k)
                .findFirst()
                .orElse(-1);
        printResult(searchInt.search(3, INTEGER_ARRAY), 2);

        Reversable reverseStr = s -> {
            StringBuilder buf = new StringBuilder();
            s.chars().forEach(x -> buf.insert(0, (char) x));
            return String.valueOf(buf);
        };
        printResult(reverseStr.reverse(TEST_STRING), 3);

        MaxSearchable searchMax = list -> Arrays
                .stream(list)
                .max(Comparator.comparing(Integer::valueOf))
                .get();
        printResult(searchMax.search(INTEGER_ARRAY), 4);

        AvgSearchable average = list -> list
                .stream()
                .mapToInt(k -> k)
                .average()
                .getAsDouble();
        printResult(average.search(INTEGER_LIST), 5);

        StrSearchable searchStr = list -> list
                .stream()
                .filter(str -> str.charAt(0) == 'a' && str.length() == 3)
                .collect(Collectors.toList());
        printResult(searchStr.search(STRING_LIST), 6);
    }

    private static <T> void printResult(T t, int taskNumber) {
        System.out.println("\n\u001B[34mTask #" + taskNumber + "\u001B[0m");
        System.out.println("Result is: \u001B[32m" + t + "\u001B[0m");
    }

}
