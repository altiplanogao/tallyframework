package com.taoswork.tallybook.general.authority.service.consumer;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class ResourceSecurityException extends Exception{
    public ResourceSecurityException() {
    }

    public ResourceSecurityException(String message) {
        super(message);
    }

    public ResourceSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceSecurityException(Throwable cause) {
        super(cause);
    }
}
