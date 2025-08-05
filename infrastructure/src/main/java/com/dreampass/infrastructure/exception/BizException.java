package com.dreampass.infrastructure.exception;

public class BizException extends RuntimeException {

    public BizException(String msg) {
        super(msg);
    }

    public BizException(String msg, Exception e) {
        super(msg, e);
    }
}
