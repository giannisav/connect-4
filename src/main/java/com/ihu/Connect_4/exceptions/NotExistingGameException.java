package com.ihu.Connect_4.exceptions;

public class NotExistingGameException extends RuntimeException {

    public NotExistingGameException(String message) {
        super(message);
    }
}
