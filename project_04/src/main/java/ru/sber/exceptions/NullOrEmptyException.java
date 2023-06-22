package ru.sber.exceptions;

/**
 * Класс ошибки вызывающейся если аргументы метода null или пустые, если аргументами являются строки или коллекции
 */
public class NullOrEmptyException extends RuntimeException {
    public NullOrEmptyException() {
        super();
    }

    public NullOrEmptyException(String message) {
        super(message);
    }

    public NullOrEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullOrEmptyException(Throwable cause) {
        super(cause);
    }
}
