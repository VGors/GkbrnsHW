package com.geekbrains.j3.lesson_03.client.interfaces;

import java.io.File;
import java.io.IOException;

public interface Logable {
    void logMsgToFile(String msg);

    void loadFromFile(int quantity);

    void logFileIni(File file) throws IOException;
}
