package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils;

import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationClass;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class NativeClassHelper_Old {
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

    public static FieldScanMethod scanAllPersistentNoSuper = new NativeClassHelper_Old.FieldScanMethod()
            .setScanSuper(false).setIncludeId(true).setIncludeStatic(false).setIncludeTransient(false);

    public static class FieldScanMethod{
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
    }

    public static Map<String, Field> getFields(Class<?> clz){
        return getFields(clz, new FieldScanMethod());
    }

    public static Map<String, Field> getFields(Class<?> clz,
                                               FieldScanMethod scanMethod){
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        boolean eof = false;
        Class<?> currentClass = clz;
        while (!eof) {
            fetchFileds(currentClass, scanMethod, fieldMap);
            if(!scanMethod.scanSuper){
                eof = true;
            }
            if (currentClass.getSuperclass() != null) {
                currentClass = currentClass.getSuperclass();
            } else {
                eof = true;
            }
        }
        return fieldMap;
    }

    private static void fetchFileds(Class<?> clz, FieldScanMethod scanMethod, Map<String, Field> fieldMap) {
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
                fieldMap.put(field.getName(), field);
            }
        }
    }

}
