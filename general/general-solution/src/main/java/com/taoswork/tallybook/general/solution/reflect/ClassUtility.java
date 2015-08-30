package com.taoswork.tallybook.general.solution.reflect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    private static boolean nullableIsAncestorOf(Class<?> ancestor, Class<?> descendant) {
        if(null == ancestor){
            return true;
        }
        if(ancestor.equals(descendant)){
            return false;
        }
        return ancestor.isAssignableFrom(descendant);
    }

    public static boolean isAncestorOf(Class<?> ancestor, Class<?> descendant) {
        if(ancestor.equals(descendant)){
            return false;
        }
        return ancestor.isAssignableFrom(descendant);
    }

    public static Collection<Class> getAllImplementedInterfaces(
        Class rootInterfaceType, Class clsType){
        Collection<Class> result = new HashSet<Class>();
        getAllImplementedInterfaces(rootInterfaceType, clsType, result);
        return result;
    }

    public static void getAllImplementedInterfaces(
        Class rootInterfaceType, Class clsType, Collection<Class> result){
        if(!nullableIsAncestorOf(rootInterfaceType, clsType)){
            return;
        }else if(clsType.isInterface()){
            result.add(clsType);
        }
        boolean notInterface = !clsType.isInterface();
        Class<?> clzSup = clsType.getSuperclass();

        if(notInterface && clzSup!= null && nullableIsAncestorOf(rootInterfaceType, clzSup)){
            getAllImplementedInterfaces(rootInterfaceType, clzSup, result);
        }

        for (Class itfType : clsType.getInterfaces()){
            getAllImplementedInterfaces(rootInterfaceType, itfType, result);
        }
    }

    public static Collection<Class> getAllImplementedInterfaces(
        Class rootInterfaceType, Class ... clsTypes){
        Set<Class> classes = new HashSet<Class>();
        for(Class cls : clsTypes){
            getAllImplementedInterfaces(rootInterfaceType, cls, classes);
        }
        return classes;
    }
}
