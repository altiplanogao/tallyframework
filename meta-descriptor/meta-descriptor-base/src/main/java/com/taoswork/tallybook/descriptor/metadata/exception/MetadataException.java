package com.taoswork.tallybook.descriptor.metadata.exception;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/6/3.
 */
public class MetadataException extends RuntimeException {
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
    public static void throwFieldConfigurationException(Field field, String message) {
        if(StringUtils.isEmpty(message)){
            throw new MetadataException("Field configuration error: " + field);
        }else {
            throw new MetadataException("Field configuration error, [" + message +"] : " + field);
        }
    }

    public static void throwFieldConfigurationException(Field field){
        throwFieldConfigurationException(field, "");
    }
}
