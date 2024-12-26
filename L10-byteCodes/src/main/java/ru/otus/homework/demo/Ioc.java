package ru.otus.homework.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.Log;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {
    }


    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static SecondTestLoggingInterface createSecondClass() {
        InvocationHandler handler = new DemoInvocationHandler(new SecondTestLogging());
        return (SecondTestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{SecondTestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private static final Set<Method> methodsToLogSet = new HashSet<>();
        private final Object myInstance;

        DemoInvocationHandler(Object myInstance) {
            this.myInstance = myInstance;
            // запоминаем методы помеченные аннотацией Log
            Arrays.stream(myInstance.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .forEach(m -> methodsToLogSet.add(m));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

            if (methodsToLogSet.stream().anyMatch(methodFromSet -> ifMethodEquals(method, methodFromSet))) {
                logMethod(method.getName(), args);

            }
            return method.invoke(myInstance, args);
        }


    }

    private static void logMethod(String methodName, Object... args) {
        String paramsString =
                Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("executing method: {}, params: {}", methodName, paramsString);
    }

    private static boolean ifMethodEquals(Method method1, Method method2) {
        return method1.getName().equals(method2.getName()) && method1.getParameterCount() == method2.getParameterCount();
    }
}


