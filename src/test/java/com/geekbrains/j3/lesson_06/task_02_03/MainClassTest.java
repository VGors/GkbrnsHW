package com.geekbrains.j3.lesson_06.task_02_03;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

class MainClassTest {

    private static MainClass mainClass;

    @AfterAll
    static void afterAllTests() {
        mainClass = null;
    }

    @Test
    void testExceptionBeyondLastFour(){
        mainClass = new MainClass();
        Throwable exception = assertThrows(RuntimeException.class, () ->{
            mainClass.beyondLastFour(new int[]{7, 8, 9, 3, 0});
            throw new RuntimeException("Runtime Exception!");
        });
        assertEquals("Runtime Exception!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("intArraysProvider")
    void testBeyondLastFour(int[] intsAssert, int[] intsSource) {
        mainClass = new MainClass();
        Assertions.assertArrayEquals(intsAssert, mainClass.beyondLastFour(intsSource));
    }

    static Stream<Arguments> intArraysProvider() {
        return Stream.of(
                Arguments.of(new int[]{0, 9}, new int[]{7, 8, 9, 4, 0, 9}),
                Arguments.of(new int[]{}, new int[]{7, 8, 9, 3, 0, 4}),
                Arguments.of(new int[]{7, 1, 2}, new int[]{4, 7, 1, 2}));
    }

    @ParameterizedTest
    @MethodSource("intArrayProvider1")
    void testContainOneOrFourFalse(int[] intsSource) {
        mainClass = new MainClass();
        Assertions.assertFalse(mainClass.containOneOrFour(intsSource));
    }

    static Stream<Arguments> intArrayProvider1() {
        return Stream.of(
                Arguments.of(new int[]{7, 8, 9, 4, 0, 9}),
                Arguments.of(new int[]{1, 9, 3, 0}),
                Arguments.of(new int[]{}));
    }

    @ParameterizedTest
    @MethodSource("intArrayProvider2")
    void testContainOneOrFourTrue(int[] intsSource) {
        mainClass = new MainClass();
        Assertions.assertTrue(mainClass.containOneOrFour(intsSource));
    }

    static Stream<Arguments> intArrayProvider2() {
        return Stream.of(
                Arguments.of(new int[]{1, 8, 9, 4, 0, 9}),
                Arguments.of(new int[]{1, 4, 1, 4}),
                Arguments.of(new int[]{1, 4}));
    }


}