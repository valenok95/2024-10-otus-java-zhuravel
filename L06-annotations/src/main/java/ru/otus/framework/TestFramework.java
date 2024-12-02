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

public class TestFramework {
    private static final Logger logger
            = LoggerFactory.getLogger(TestClass.class);
    private static List<Method> beforeEachMethodList = new ArrayList<>();
    private static List<Method> afterEachMethodList = new ArrayList<>();
    private static List<Method> testList = new ArrayList<>();
    private static int successCount = 0;

    static void runTestClass(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeEachMethodList.add(method);
                continue;
            }
            if (method.isAnnotationPresent(After.class)) {
                afterEachMethodList.add(method);
                continue;
            }
            if (method.isAnnotationPresent(Test.class)) {
                testList.add(method);
            }
        }

        for (Method test : testList) {
            TestClass currentTestInstance = ReflectionHelper.instantiate(TestClass.class);
            boolean isBeforeError = false;
            // подготовка к тесту
            for (Method beforeEachMethod : beforeEachMethodList) {
                if (!ReflectionHelper.callMethodAndCheckSuccess(currentTestInstance,
                        beforeEachMethod)) {
                    isBeforeError = true;
                    break;
                }
            }
            if (isBeforeError) {
                continue;
            }
// запуск теста
            if (ReflectionHelper.callMethodAndCheckSuccess(currentTestInstance, test)) {
                logger.info("test \"{}\" has passed!", test.getName());
                successCount++;
            }
//завершение
            for (Method afterEachMethod : afterEachMethodList) {
                ReflectionHelper.callMethodAndCheckSuccess(currentTestInstance, afterEachMethod);
            }

        }
        logger.info("Passed: {} , Failed: {}", successCount, testList.size() - successCount);
    }
}
