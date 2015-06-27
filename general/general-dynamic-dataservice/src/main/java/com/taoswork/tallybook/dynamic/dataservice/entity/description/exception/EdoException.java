package com.taoswork.tallybook.dynamic.dataservice.entity.description.exception;

/**
 * Created by Gao Yuan on 2015/6/3.
 */
public class EdoException extends RuntimeException {
    public EdoException() {
    }

    public EdoException(String message) {
        super(message);
    }

    public EdoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EdoException(Throwable cause) {
        super(cause);
    }

    public EdoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
