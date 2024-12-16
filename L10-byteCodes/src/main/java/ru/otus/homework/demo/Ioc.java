package ru.otus.homework.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.Log;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);
    private static List<Method> methodsToLog = new ArrayList<>();

    private Ioc() {
    }

    static <T> T createMyClass(Class<T> myInterface) {
        InvocationHandler handler = new Ioc.DemoInvocationHandler(myInterface);

        // запоминаем методы помеченные аннотацией Log
        Arrays.stream(myInterface.getMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .forEach(m -> methodsToLog.add(m));


        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{myInterface},
                handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Class<?> myClass;

        DemoInvocationHandler(Class<?> myInterface) {
            this.myClass = myInterface; // не понимаю как вызвать реализацию интерфейса (инстанс).
            //  откуда мы будем знать какая реализация требуется?
            //Class.forName(myInterface.getName().replace("Interface", ""));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

            if (methodsToLog.contains(method)) {
                logMethod(method.getName(), args);

            }
            return method.invoke(myClass, args);
        }


    }

    private static void logMethod(String methodName, Object... args) {
        String paramsString =
                Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        logger.info("executing method: {}, params: {}", methodName, paramsString);
    }
}


