package com.taoswork.tallybook.dynamic.datameta.metadata.utils;

import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationClass;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class NativeClassHelper {
    public static boolean isExcludeClassFromPolymorphism(Class<?> clazz) {
        //We filter out abstract classes because they can't be instantiated.
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return true;
        }

        //We filter out classes that are marked to exclude from polymorphism
        PresentationClass adminPresentationClass = clazz.getAnnotation(PresentationClass.class);
        if (adminPresentationClass == null) {
            return false;
        } else if (adminPresentationClass.excludeFromPolymorphism()) {
            return true;
        }
        return false;
    }

    public static FieldScanMethod scanAllPersistentNoSuper = new NativeClassHelper.FieldScanMethod()
            .setScanSuper(false).setIncludeId(true).setIncludeStatic(false).setIncludeTransient(false);

    public static class FieldScanMethod {
        boolean includeId = false;
        boolean includeStatic = false;
        boolean includeTransient = false;
        boolean scanSuper = true;

        public FieldScanMethod setIncludeId(boolean includeId) {
            this.includeId = includeId;
            return this;
        }

        public FieldScanMethod setIncludeStatic(boolean includeStatic) {
            this.includeStatic = includeStatic;
            return this;
        }

        public FieldScanMethod setIncludeTransient(boolean includeTransient) {
            this.includeTransient = includeTransient;
            return this;
        }

        public FieldScanMethod setScanSuper(boolean scanSuper) {
            this.scanSuper = scanSuper;
            return this;
        }


        public Class<?> getTheSuper(Class<?> clz) {
            if (!this.scanSuper) {
                return null;
            } else {
                return clz.getSuperclass();
            }
        }
    }

    public static List<Field> getFields(Class<?> clz){
        return getFields(clz, new FieldScanMethod());
    }

    public static List<Field> getFields(Class<?> clz,
                                        FieldScanMethod scanMethod){
        List<Field> fieldList = new ArrayList<Field>();
        scanHierarchySuperFirst(clz, scanMethod, fieldList);

        return fieldList;
    }

    private static void scanHierarchySuperFirst(Class<?> clz, FieldScanMethod scanMethod, List<Field> fieldList){
        Class<?> superClz = scanMethod.getTheSuper(clz);

        if(superClz != null){
            scanHierarchySuperFirst(superClz, scanMethod, fieldList);
        }

        fetchFileds(clz, scanMethod, fieldList);
    }

    private static void scanHierarchyChildFirst(Class<?> clz, FieldScanMethod scanMethod, List<Field> fieldList){
        Class<?> superClz = scanMethod.getTheSuper(clz);

        fetchFileds(clz, scanMethod, fieldList);

        if(superClz != null){
            scanHierarchyChildFirst(superClz, scanMethod, fieldList);
        }
    }

    private static void fetchFileds(Class<?> clz, FieldScanMethod scanMethod, List<Field> fieldList) {
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields){
            boolean abandon = false;
            int modifiers = field.getModifiers();
            if(field.isAnnotationPresent(Id.class)){
                if(!scanMethod.includeId){
                    abandon = true;
                }
            }
            if(Modifier.isStatic(modifiers)){
                if(!scanMethod.includeStatic){
                    abandon = true;
                }
            }
            if(Modifier.isTransient(modifiers) ||
                    field.isAnnotationPresent(Transient.class)){
                if(!scanMethod.includeTransient){
                    abandon = true;
                }
            }
            if(!abandon){
                fieldList.add(field);
            }
        }
    }
}