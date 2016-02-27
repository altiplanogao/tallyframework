package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class ListFacet implements IFacet {
    protected final Class listType;
    protected final Class listImplType;
    protected Class entryTargetClass;
    //declared entry class is not important;

    public ListFacet(Class collectionType, Class listImplType,
                     Class entryTargetClass) {
        this.listType = collectionType;
        this.listImplType = listImplType;
        this.entryTargetClass = entryTargetClass;
    }

    public ListFacet(ListFacet other) {
        this.listType = other.listType;
        this.listImplType = other.listImplType;
        this.entryTargetClass = other.entryTargetClass;
    }

    public ListFacet(Field field, boolean useAnnotation, Class explicitEntryTargetClass){
        if(useAnnotation){
            CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
            if (cf == null) {
                MetadataException.throwFieldConfigurationException(field, "@CollectionField not defined");
            }
            Class collectionClz = field.getType();
            Class targetClz = cf.targetClass();
            if (targetClz.equals(void.class)) {
                targetClz = GenericTypeUtility.getGenericArgument(field, 0);
            }
            Class collectionImplClz = cf.collectionImplementationClass();
            if (collectionImplClz.equals(void.class)) {
                collectionImplClz = workOutCollectionImplementType(collectionClz);
            }
            this.listType = collectionClz;
            this.listImplType = collectionImplClz;
            this.entryTargetClass = targetClz;
        }else {
            this.listType = field.getType();
            this.listImplType = workOutCollectionImplementType(listType);
            this.entryTargetClass = GenericTypeUtility.getGenericArgument(field, 0);
        }
        if(explicitEntryTargetClass != null && !void.class.equals(explicitEntryTargetClass)){
            this.entryTargetClass = explicitEntryTargetClass;
        }
    }

    public static ListFacet byAnnotation(Field field){
        if(field.isAnnotationPresent(CollectionField.class)){
            return new ListFacet(field, true, null);
        }
        return null;
    }

    public Class getListType() {
        return listType;
    }

    public Class getListImplType() {
        return listImplType;
    }

    public void setEntryTargetClass(Class entryTargetClass) {
        this.entryTargetClass = entryTargetClass;
    }

    public Class getEntryTargetClass() {
        return entryTargetClass;
    }

    public static Class workOutCollectionImplementType(Class collectionType) {
        try {
            Constructor constructor = collectionType.getConstructor(new Class[]{});
            return collectionType;
        } catch (NoSuchMethodException e) {
            //Ignore this exception, because it is not an instantiatable object.
        }
        if (List.class.equals(collectionType)) {
            return ArrayList.class;
        } else if (Set.class.equals(collectionType)) {
            return HashSet.class;
        } else if (Collection.class.equals(collectionType)) {
            return ArrayList.class;
        } else {
            return ArrayList.class;
        }
    }
}
