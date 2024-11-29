package ru.otus.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.framework.annotations.After;
import ru.otus.framework.annotations.Before;
import ru.otus.framework.annotations.Test;

public class TestClass {
    private static final Logger logger
            = LoggerFactory.getLogger(TestClass.class);
@Before
    void setUp() {
        logger.info("setting up before each test");
    }
@After
    void shutDown() {
        logger.info("shut down after each test");
    }

@Test
    void someTest() {
        var result = 10 / 2;
        if (result != 5) {
            throw new ArithmeticException("Wrong Math");
        }
    }
@Test
    void anotherTest() {
        var result = 10 / 0;
        if (result != 5) {
            throw new ArithmeticException("Wrong Math");
        }
    }

}
