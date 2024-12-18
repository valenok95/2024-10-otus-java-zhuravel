package ru.otus.homework.demo;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createMyClass();
        testLogging.calculation(6, 5);
        testLogging.calculation(6);


        SecondTestLoggingInterface secondTestLogging = Ioc.createSecondClass();
        secondTestLogging.sum(7);
        secondTestLogging.sum(7, 8, 9);

    }
}
