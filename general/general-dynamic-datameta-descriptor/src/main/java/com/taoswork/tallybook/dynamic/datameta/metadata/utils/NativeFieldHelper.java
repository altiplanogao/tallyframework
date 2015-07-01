package com.taoswork.tallybook.dynamic.datameta.metadata.utils;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class NativeFieldHelper {
    public static Field getSingleField(Class<?> clazz, String fieldName) throws IllegalStateException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsf) {
            // Try superclass
            if (clazz.getSuperclass() != null) {
                return getSingleField(clazz.getSuperclass(), fieldName);
            }

            return null;
        }
    }
}
