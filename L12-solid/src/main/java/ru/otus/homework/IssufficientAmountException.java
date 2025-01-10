package ru.otus.homework;

public class IssufficientAmountException extends RuntimeException {
    public IssufficientAmountException(String message) {
        super(message);
    }
}
