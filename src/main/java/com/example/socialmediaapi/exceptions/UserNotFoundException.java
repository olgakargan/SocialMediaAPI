package com.example.socialmediaapi.exceptions;


/**
 * Исключение пользователь не нашел
 */
public class UserNotFoundException extends Exception {
    String message;

    public UserNotFoundException(String str) {
        message = str;
    }

    public String toString() {
        return (message);
    }
}