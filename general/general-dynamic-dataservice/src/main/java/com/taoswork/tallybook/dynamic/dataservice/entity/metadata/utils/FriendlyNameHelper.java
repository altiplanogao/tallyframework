package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FriendlyNameHelper {
    public static String makeFriendlyName4Class(Class<?> clz){
        return clz.getSimpleName() + "_" + clz.getSimpleName();
    }
    public static String makeFriendlyName4ClassTab(Class<?> clz, String tabName){
        return clz.getSimpleName() + "_Tab_" + tabName;
    }
    public static String makeFriendlyName4ClassGroup(Class<?> clz, String groupName){
        return clz.getSimpleName() + "_Group_" + groupName;
    }
    public static String makeFriendlyName4Field(Field field){
        Class<?> clz = field.getDeclaringClass();
        return clz.getSimpleName() + "_Field_" + field.getName();
    }
}
