package com.taoswork.tallybook.descriptor.dataio.copier;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class CopyException extends Exception {
    public CopyException() {
    }

    public CopyException(String message) {
        super(message);
    }

    public CopyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopyException(Throwable cause) {
        super(cause);
    }

    public CopyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
