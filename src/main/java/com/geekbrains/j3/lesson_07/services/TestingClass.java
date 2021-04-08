package com.geekbrains.j3.lesson_07.services;

import com.geekbrains.j3.lesson_07.TestClass;
import com.geekbrains.j3.lesson_07.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class TestingClass {
    private static boolean beforeIsPresent = false;
    private static boolean afterIsPresent = false;
    private static Map<Method, Integer> priorityMap;

    public static void start(Class testClass) {
        priorityMap = getMethodsToSortedMap(testClass);
        printMap(priorityMap);
    }

    private static Map<Method, Integer> getMethodsToSortedMap(Class testClass) {
        Map<Method, Integer> tmpMap = new HashMap<>();
        Method[] methods = testClass.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getAnnotation(Test.class) != null) {
                int priority = m.getDeclaredAnnotation(Test.class).value();
                if (priority >= 1 && priority <= 10) {
                    tmpMap.put(m, priority);
                } else {
                    throw new RuntimeException("Wrong priority in \"Test\" annotation");
                }
            } else if (m.getAnnotation(BeforeSuite.class) != null) {
                if (!beforeIsPresent) {
                    beforeIsPresent = true;
                    tmpMap.put(m, 0);
                } else {
                    throw new RuntimeException("More then 1 \"BeforeSuite\" annotations");
                }
            } else if (m.getAnnotation(AfterSuite.class) != null) {
                if (!afterIsPresent){
                    afterIsPresent = true;
                    tmpMap.put(m, 11);
                } else {
                    throw new RuntimeException("More then 1 \"AfterSuite\" annotations");
                }
            }
        }
        return sortMap(tmpMap);
    }

    private static void printMap (Map<Method, Integer> map){
        map.forEach((k, j) -> {
            try {
                k.invoke(new TestClass());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static Map<Method, Integer> sortMap (Map<Method, Integer> unsortedMap){
        return unsortedMap.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey,Entry::getValue, (e1,e2)->e1,LinkedHashMap::new));
    }
}
