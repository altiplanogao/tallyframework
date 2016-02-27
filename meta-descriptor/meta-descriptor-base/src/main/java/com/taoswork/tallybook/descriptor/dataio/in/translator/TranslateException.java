package com.taoswork.tallybook.descriptor.dataio.in.translator;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class TranslateException extends Exception {
    public TranslateException() {
    }

    public TranslateException(String message) {
        super(message);
    }

    public TranslateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslateException(Throwable cause) {
        super(cause);
    }
}
