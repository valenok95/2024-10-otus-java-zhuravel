package ru.otus.homework.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.Log;

public class TestLogging implements TestLoggingInterface {

    private static final Logger logger
            = LoggerFactory.getLogger(TestLogging.class);

    @Override
    public void calculation(int param) {
        logger.info("{} + 0  = {}", param, param);
    }

    @Override
    public void calculation(int paramFirst, int paramSecond) {
        logger.info("{} + {}  = {}", paramFirst, paramSecond, paramFirst + paramSecond);
    }
}
