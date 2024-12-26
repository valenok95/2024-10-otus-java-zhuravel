package ru.otus.homework.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.Log;

public class SecondTestLogging implements SecondTestLoggingInterface {

    private static final Logger logger
            = LoggerFactory.getLogger(SecondTestLogging.class);

    @Override
    public void sum(int param) {
        logger.info("{} + 0  = {}", param, param);
    }

    @Log
    @Override
    public void sum(int param, int param2, int param3) {
        logger.info("{} + {} + {}  = {}", param, param2, param3, param + param2 + param3);
    }
}
