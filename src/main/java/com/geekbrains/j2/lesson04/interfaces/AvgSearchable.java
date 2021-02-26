package com.geekbrains.j2.lesson04.interfaces;

import java.util.List;

@FunctionalInterface
public interface AvgSearchable {
    Double search(List<Integer> list);
}
