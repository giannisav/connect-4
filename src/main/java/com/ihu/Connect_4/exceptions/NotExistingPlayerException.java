package com.ihu.Connect_4.exceptions;

public class NotExistingPlayerException extends RuntimeException {

    public NotExistingPlayerException(String message) {
        super(message);
    }
}
