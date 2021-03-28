package com.geekbrains.j3.lesson_04.task2.server.interfaces;

import java.io.IOException;

public interface Censurable {
    void censureFileIni() throws IOException;
    String msgFilter(String msg);
}
