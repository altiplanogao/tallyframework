package com.taoswork.tallybook.descriptor.metadata;

import com.taoswork.tallybook.descriptor.metadata.friendly.IFriendly;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public interface IClassMeta extends IFriendly, Cloneable, Serializable {
    //Main
    Class<?> getEntityClz();

    boolean containsSuper();

    boolean containsHierarchy();

    String getIdFieldName();

    Field getIdField();

    String getNameFieldName();

    Field getNameField();

    //Tab, Group, Field metadata
    Map<String, TabMeta> getReadonlyTabMetaMap();

    Map<String, GroupMeta> getReadonlyGroupMetaMap();

    Map<String, IFieldMeta> getReadonlyFieldMetaMap();

    IFieldMeta getFieldMeta(String fieldName);

    boolean hasField(String fieldName);

    Collection<String> getCollectionFields();

    Collection<String> getNonCollectionFields();

    //Referencing
    boolean isReferencingClassMetaPublished();

    Map<String, IClassMeta> getReadonlyReferencingClassMetaMap();

    IClassMeta getReferencingClassMeta(Class entity);

    //Validator ,Gate and Copier
    Collection<String> getReadonlyValidators();

    Collection<String> getReadonlyValueGates();

    String getValueCopier();

    //Clone
    IClassMeta clone();
}
