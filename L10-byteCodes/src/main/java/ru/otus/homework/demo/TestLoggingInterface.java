package ru.otus.homework.demo;

import ru.otus.homework.annotation.Log;

public interface TestLoggingInterface {

    @Log
    void calculation(int param);

    @Log
    void calculation(int paramFirst, int paramSecond);
}
