package com.example.socialmediaapi.exceptions;


/**
 * Исключение с неверным идентификатором
 */
public class IncorrectIdException extends Exception {
    String message;

    public IncorrectIdException(String str) {
        message = str;
    }

    public String toString() {
        return (message);
    }
}
