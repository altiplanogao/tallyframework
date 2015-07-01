package com.taoswork.tallybook.dynamic.datameta.metadata.exception;

/**
 * Created by Gao Yuan on 2015/6/3.
 */
public class MetadataException extends RuntimeException{
    public MetadataException() {
    }

    public MetadataException(String message) {
        super(message);
    }

    public MetadataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataException(Throwable cause) {
        super(cause);
    }

    public MetadataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
