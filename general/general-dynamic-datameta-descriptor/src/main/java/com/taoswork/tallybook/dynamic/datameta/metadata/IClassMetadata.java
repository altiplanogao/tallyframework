package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendly;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public interface IClassMetadata extends IFriendly, Cloneable, Serializable {
    //Main
    Class<?> getEntityClz();

    boolean containsSuper();

    String getIdFieldName();

    Field getIdField();

    String getNameFieldName();

    Field getNameField();

    //Tab, Group, Field metadata
    Map<String, TabMetadata> getReadonlyTabMetadataMap();

    Map<String, GroupMetadata> getReadonlyGroupMetadataMap();

    Map<String, IFieldMetadata> getReadonlyFieldMetadataMap();

    IFieldMetadata getFieldMetadata(String fieldName);

    boolean hasField(String fieldName);

    Collection<String> getNonCollectionFields();

    //Referencing
    boolean isReferencingClassMetadataPublished();

    Map<String, IClassMetadata> getReadonlyReferencingClassMetadataMap();

    IClassMetadata getReferencingClassMetadata(Class entity);

    //Validator and Gate
    Collection<String> getReadonlyValidators();

    Collection<String> getReadonlyValueGates();

    //Clone
    IClassMetadata clone();
}
