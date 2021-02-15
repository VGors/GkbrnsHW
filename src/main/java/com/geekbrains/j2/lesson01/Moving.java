package com.geekbrains.j2.lesson01;

public interface Moving {
    boolean run(int distance);

    boolean jump(int height);

    void reduceJumpReserve(int quantity);

    void reduceRunReserve(int quantity);

    void actionSelector(Object[] obstacle);
}
