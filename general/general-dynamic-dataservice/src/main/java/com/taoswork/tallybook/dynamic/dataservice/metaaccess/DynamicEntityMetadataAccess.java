package com.taoswork.tallybook.dynamic.dataservice.metaaccess;

import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;

import java.util.Collection;
import java.util.Locale;

/**
 * Assume we have classes as following:
 * interface IPerson{}                  [entity interface]
 * class Person implements IPerson{}    [entity class],[entity root class]
 * class Male extends Person{}          [entity class]
 * class Female extends Person{}        [entity class]
 * <p>
 * Parameter rule:
 * 1. entityInterface, means the input should be IPerson
 * 2. entityClass, means the input should be Person, Male or Female
 * 3. entityType, means the input could be entityInterface or entityClass
 */
public interface DynamicEntityMetadataAccess {
    public static final String COMPONENT_NAME = "DynamicEntityMetadataAccess";

    Collection<Class> getAllEntityInterfaces();

    <T> Class<T> getRootInstanceableEntityClass(Class<T> entityType);

    EntityClassTree getEntityClassTree(Class<?> entityType);

    ClassTreeMetadata getClassTreeMetadata(Class<?> entityType);

    IEntityInfo getEntityInfo(Class<?> entityType, Locale locale, EntityInfoType infoType);
}
