package com.ihu.Connect_4.exceptions;

public class UnauthorizedPlayerException extends RuntimeException {
    public UnauthorizedPlayerException(String message) {
        super(message);
    }
}
