package com.taoswork.tallybook.dynamic.datameta.metadata.utils;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;

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
    public static FieldScanMethod scanAllPersistentNoSuper = new NativeClassHelper.FieldScanMethod()
        .setScanSuper(false).setIncludeId(true).setIncludeStatic(false).setIncludeTransient(false);

    public static Class[] getSuperClasses(Class clz, boolean reverseOrder) {
        return getSuperClasses(clz, reverseOrder, false);
    }

    public static Class[] getSuperClasses(Class clz, boolean reverseOrder, boolean includeObject) {
        boolean skipObject = !includeObject;
        List<Class> classes = new ArrayList<Class>();
        if (clz != null) {
            do {
                clz = clz.getSuperclass();
                if(Object.class.equals(clz) && skipObject)
                    continue;
                if (clz != null) {
                    if (reverseOrder) {
                        classes.add(0, clz);
                    } else {
                        classes.add(clz);
                    }
                } else {
                    break;
                }
            } while (true);
        }
        return classes.toArray(new Class[classes.size()]);
    }

    public static boolean isInstantiable(Class<?> clazz) {
        //We filter out abstract classes because they can't be instantiated.
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return false;
        }

        //We filter out classes that are marked to exclude from polymorphism
        PresentationClass presentationClass = clazz.getAnnotation(PresentationClass.class);
        if (presentationClass == null) {
            return true;
        } else {
            return presentationClass.instantiable();
        }
    }

    public static List<Field> getFields(Class<?> clz) {
        return getFields(clz, new FieldScanMethod());
    }

    public static List<Field> getFields(Class<?> clz,
                                        FieldScanMethod scanMethod) {
        List<Field> fieldList = new ArrayList<Field>();
        scanHierarchySuperFirst(clz, scanMethod, fieldList);

        return fieldList;
    }

    private static void scanHierarchySuperFirst(Class<?> clz, FieldScanMethod scanMethod, List<Field> fieldList) {
        Class<?> superClz = scanMethod.getTheSuper(clz);

        if (superClz != null) {
            scanHierarchySuperFirst(superClz, scanMethod, fieldList);
        }

        fetchFileds(clz, scanMethod, fieldList);
    }

    private static void scanHierarchyChildFirst(Class<?> clz, FieldScanMethod scanMethod, List<Field> fieldList) {
        Class<?> superClz = scanMethod.getTheSuper(clz);

        fetchFileds(clz, scanMethod, fieldList);

        if (superClz != null) {
            scanHierarchyChildFirst(superClz, scanMethod, fieldList);
        }
    }

    private static void fetchFileds(Class<?> clz, FieldScanMethod scanMethod, List<Field> fieldList) {
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            boolean abandon = false;
            int modifiers = field.getModifiers();
            if (field.isAnnotationPresent(Id.class)) {
                if (!scanMethod.includeId) {
                    abandon = true;
                }
            }
            if (Modifier.isStatic(modifiers)) {
                if (!scanMethod.includeStatic) {
                    abandon = true;
                }
            }
            if (Modifier.isTransient(modifiers) ||
                field.isAnnotationPresent(Transient.class)) {
                if (!scanMethod.includeTransient) {
                    abandon = true;
                }
            }
            //Version still needed when doing value copy
//            if (field.isAnnotationPresent(Version.class)) {
//                abandon = true;
//            }
            if (!abandon) {
                fieldList.add(field);
            }
        }
    }

    public static Field getFieldOfName(Class clz, String fieldName, boolean checkSuper) {
        do {
            try {
                Field field = clz.getDeclaredField(fieldName);
                if (field != null) {
                    return field;
                }
            } catch (NoSuchFieldException e) {
            }
            if (checkSuper) {
                clz = clz.getSuperclass();
            } else {
                return null;
            }
        } while (clz != null);
        return null;
    }

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
}
