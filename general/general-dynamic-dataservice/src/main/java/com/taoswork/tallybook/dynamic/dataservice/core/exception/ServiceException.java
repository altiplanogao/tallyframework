package com.taoswork.tallybook.dynamic.dataservice.core.exception;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class ServiceException extends Exception{
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
