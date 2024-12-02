package ru.otus.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S3011", "java:S112"})
public class ReflectionHelper {
    private static final Logger logger
            = LoggerFactory.getLogger(ReflectionHelper.class);

    private ReflectionHelper() {
    }

    public static boolean callMethodAndCheckSuccess(Object object, Method method, Object... args) {
        try {
            method.setAccessible(true);
            method.invoke(object, args);
            return true;
        } catch (Exception e) {
            logger.error("invoke method error", e);
            return false;
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
