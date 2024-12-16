package ru.otus.homework.demo;

import java.lang.reflect.InvocationHandler;
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

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new Ioc.DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
            Arrays.stream(myClass.getClass().getDeclaredMethods()).forEach(method -> {
                try {
                    if (myClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                        methodsToLog.add(method);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            methodsToLog.forEach(methotToLog->{
                if(methotToLog.getName().equals(method.getName()) && methotToLog.getParameterTypes().length == method.getParameterTypes().length){
                    String paramsString =
                            Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
                    try {
                        if (myClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                            logger.info("executing method: {}, params: {}", method.getName(), paramsString);
                        }
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return method.invoke(myClass, args);
        }
    }
}
