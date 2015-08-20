package com.taoswork.tallybook.general.authority.core.basic;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityException extends Exception {
    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }
}
