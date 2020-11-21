package com.ihu.Connect_4.exceptions;

public class InvalidTurnPlayException extends RuntimeException {
    public InvalidTurnPlayException(String message) {
        super(message);
    }
}
