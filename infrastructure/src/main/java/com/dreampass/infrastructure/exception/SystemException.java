package com.dreampass.infrastructure.exception;

public class SystemException extends RuntimeException {

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(String msg, Exception e) {
        super(msg, e);
    }
}
