package com.taoswork.tallybook.descriptor.metadata.utils;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FriendlyNameHelper {
    public static String makeFriendlyName4Class(Class<?> clz) {
        return clz.getSimpleName() + "_" + clz.getSimpleName();
    }

    public static String makeFriendlyName4ClassTab(Class<?> clz, String tabName) {
        return clz.getSimpleName() + "_Tab_" + tabName;
    }

    public static String makeFriendlyName4ClassGroup(Class<?> clz, String groupName) {
        return clz.getSimpleName() + "_Group_" + groupName;
    }

    public static String makeFriendlyName4Field(Field field) {
        Class<?> clz = field.getDeclaringClass();
        return clz.getSimpleName() + "_Field_" + field.getName();
    }

    public static String makeFriendlyName4EnumClass(Class<?> enumClz) {
        String simpleName = enumClz.getSimpleName();
        return "Enum_" + simpleName;
    }

    public static String makeFriendlyName4EnumValue(Class<?> enumClz, Object value) {
        String simpleName = enumClz.getSimpleName();
        return "Enum_" + simpleName + "_Val_" + value.toString();
    }

}
