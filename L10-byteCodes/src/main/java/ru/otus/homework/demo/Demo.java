package ru.otus.homework.demo;
public class Demo {
        public static void main(String[] args){
            TestLoggingInterface testLogging = Ioc.createMyClass(TestLoggingInterface.class);
            testLogging.calculation(6,5); 
            testLogging.calculation(6); 
    }
}
