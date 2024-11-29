package ru.otus.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.framework.annotations.After;
import ru.otus.framework.annotations.Before;
import ru.otus.framework.annotations.Test;
import ru.otus.reflection.ReflectionHelper;

public class TestRunner {
    private static final Logger logger
            = LoggerFactory.getLogger(TestClass.class);
    private static List<String> beforeEachMethodList = new ArrayList<>();
    private static List<String> afterEachMethodList = new ArrayList<>();
    private static List<String> testList = new ArrayList<>();
    private static int successCount = 0;


    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;
        Method[] methods = testClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeEachMethodList.add(method.getName());
                continue;
            }
            if (method.isAnnotationPresent(After.class)) {
                afterEachMethodList.add(method.getName());
                continue;
            }
            if (method.isAnnotationPresent(Test.class)) {
                testList.add(method.getName());
            }
        }

        for (String testName : testList) {
            TestClass currentTestInstance = ReflectionHelper.instantiate(TestClass.class);
            // подготовка к тесту
            for (String beforeEachMethodName : beforeEachMethodList) {
                ReflectionHelper.callMethod(currentTestInstance, beforeEachMethodName);
            }
// запуск теста
            try {
                ReflectionHelper.callMethod(currentTestInstance, testName);
                logger.info("test \"{}\" has passed!", testName);
                successCount++;
            } catch (Exception e) {
                logger.error("test \"{}\" has failed with error: {}!", testName,
                        e.getCause().getCause().getMessage());
            }
//завершение
            for (String afterEachMethodName : afterEachMethodList) {
                ReflectionHelper.callMethod(currentTestInstance, afterEachMethodName);
            }

        }
        logger.info("Passed: {} , Failed: {}", successCount, testList.size() - successCount);
    }

}