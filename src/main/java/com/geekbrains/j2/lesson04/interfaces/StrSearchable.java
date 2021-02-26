package com.geekbrains.j2.lesson04.interfaces;

import java.util.List;

@FunctionalInterface
public interface StrSearchable {
    List<String> search(List<String> list);
}
