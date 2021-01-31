package com.ihu.Connect_4.exceptions;

public class XssException extends RuntimeException{
    public XssException(String message) {
        super(message);
    }
}
