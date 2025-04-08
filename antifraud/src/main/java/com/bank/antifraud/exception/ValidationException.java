package com.bank.antifraud.exception;

/**
 * Пользовательское исключение для обработки ошибок валидации.
 */
public class ValidationException extends RuntimeException {

    /**
     * Конструктор с сообщением об ошибке.
     *
     * @param message Сообщение об ошибке.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Конструктор с сообщением об ошибке и причиной.
     *
     * @param message Сообщение об ошибке.
     * @param cause   Причина возникновения исключения.
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}