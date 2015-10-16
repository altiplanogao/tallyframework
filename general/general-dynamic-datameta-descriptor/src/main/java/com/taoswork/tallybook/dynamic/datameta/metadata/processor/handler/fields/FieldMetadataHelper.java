package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;

import javax.persistence.*;
import java.lang.reflect.Field;

public class FieldMetadataHelper {

    public static Class getToOneTargetType(Field field,
                                           boolean checkO2O, boolean checkM2O) {
        if (null == field)
            return null;
        if (checkO2O && (null != field.getDeclaredAnnotation(OneToOne.class))) {
            OneToOne oneToOne = field.getDeclaredAnnotation(OneToOne.class);
            Class fieldType = field.getType();
            Class targetType = oneToOne.targetEntity();
            if (void.class.equals(targetType)) {
                targetType = fieldType;
            }
            return targetType;
        }
        if (checkM2O && (null != field.getDeclaredAnnotation(ManyToOne.class))) {
            ManyToOne manyToOne = field.getDeclaredAnnotation(ManyToOne.class);
            Class fieldType = field.getType();
            Class targetType = manyToOne.targetEntity();
            if (void.class.equals(targetType)) {
                targetType = fieldType;
            }
            return targetType;
        }
        return null;

    }

    public static Class getCollectionTargetType(Field field,
                                                boolean checkO2M, boolean checkM2M,
                                                boolean checkCollection) {
        if (checkO2M && (null != field.getDeclaredAnnotation(OneToMany.class))) {
            OneToMany oneToMany = field.getDeclaredAnnotation(OneToMany.class);
            Class targetType = oneToMany.targetEntity();
            if (void.class.equals(targetType)) {
                return null;
            }
            return targetType;
        }
        if (checkM2M && (null != field.getDeclaredAnnotation(ManyToMany.class))) {
            ManyToMany manyToMany = field.getDeclaredAnnotation(ManyToMany.class);
            Class targetType = manyToMany.targetEntity();
            if (void.class.equals(targetType)) {
                return null;
            }
            return targetType;
        }
        if (checkCollection && (null != field.getDeclaredAnnotation(ElementCollection.class))) {
            ElementCollection elementCollection = field.getDeclaredAnnotation(ElementCollection.class);
            Class targetType = elementCollection.targetClass();
            if (void.class.equals(targetType)) {
                return null;
            }
            return targetType;
        }
        return null;
    }

    public static boolean isEmbeddable(Class cls) {
        return cls.isAnnotationPresent(Embeddable.class);
    }

    public static boolean isEntity(Class cls) {
        return cls.isAnnotationPresent(Entity.class);
    }

    public static ClassMetadata generateEmbeddedClassMetadata(ClassProcessor classProcessor, Class embeddableClass) {
        if (embeddableClass.isAnnotationPresent(Embeddable.class)) {
            ClassMetadata embeddedMetadata = new ClassMetadata();
            ProcessResult innerResult = classProcessor.process(embeddableClass, embeddedMetadata);
            if (innerResult == ProcessResult.HANDLED) {
                return embeddedMetadata;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
