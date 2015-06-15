package com.taoswork.tallybook.general.solution.reflect;

/**
 * Created by Gao Yuan on 2015/6/14.
 */
public class ClassUtility {
    public static Class getSuperClassWithout(Class clz, String fragment){
        if(!clz.getName().toLowerCase().contains(fragment)){
            return clz;
        }
        Class inputClz = clz;
        while (clz.getSimpleName().toLowerCase().contains(fragment)){
            clz = clz.getSuperclass();
            if(clz == null){
                return inputClz;
            }
        }
        return clz;
    }
}
