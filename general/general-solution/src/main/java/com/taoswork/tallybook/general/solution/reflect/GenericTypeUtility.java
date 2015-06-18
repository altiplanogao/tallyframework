package com.taoswork.tallybook.general.solution.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by Gao Yuan on 2015/6/18.
 */
public class GenericTypeUtility {
    public static boolean isGenericType(Class clz) {
        TypeVariable<Class<?>>[] typeVariables = clz.getTypeParameters();
        return !(typeVariables == null || typeVariables.length == 0);
    }

    public static boolean isTypeArgumentMissing(
            Field field) {
        return isTypeArgumentMissing(
                field.getGenericType());
    }

    public static boolean isTypeArgumentMissing(
            Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return _isTypeArgumentMissing(parameterizedType);
        }else if (type instanceof Class) {
            Class clazz = (Class)type;
            TypeVariable<Class<?>>[] typeVariables = clazz.getTypeParameters();
            return (typeVariables == null || typeVariables.length == 0) ? false : true;
        }else {
            throw new RuntimeException("Unexpected Raw Type @ GenericTypeUtility.class");
        }
    }

    private static boolean _isTypeArgumentMissing(
            ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        int expected = 0;

        if (rawType instanceof Class) {
            Class clazz = (Class) rawType;
            TypeVariable<Class<?>>[] typeVariables = clazz.getTypeParameters();
            expected = (typeVariables == null) ? 0 : typeVariables.length;
        } else {
            throw new RuntimeException("Unexpected Raw Type @ GenericTypeUtility.class");
        }

        if (expected == 0) {
            return false;
        }

        Type[] typeArgs = parameterizedType.getActualTypeArguments();
        if (typeArgs == null) {
            return true;
        }

        if (typeArgs.length != expected) {
            return true;
        }

        int actual = 0;
        for (Type tp : typeArgs) {
            if (tp != null) {
                if (isTypeArgumentMissing(tp)) {
                    return true;
                }else {
                    actual++;
                }
            }
        }
        return expected != actual;
    }
}
