package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
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

    /**
     * Returns all the instance-able entity-types
     * @return
     */
    Collection<Class> getAllEntityTypes();

    /**
     * Returns all the interfaces that referenced by instance-able entity-types
     * @return
     */
    Collection<Class> getAllEntityInterfaces();

    /**
     * Get the root instance-able entity-type
     * @param entityCeilingType
     * @param <T>
     * @return
     */
    <T> Class<T> getRootInstanceableEntityClass(Class<T> entityCeilingType);

    /**
     * Get all the instance-able entity-types derived from entityCeilingType
     * @param entityCeilingType, the super entity-type of required instance-able entity-types
     * @return
     */
    Collection<Class> getInstanceableEntityTypes(Class<?> entityCeilingType);

    /**
     * Get a class tree with root (root's type is entityCeilingType)
     * @param entityCeilingType, the root entity-type of required EntityClassTree
     * @return
     */
    EntityClassTree getEntityClassTree(Class<?> entityCeilingType);

//    /**
//     * Get ClassTreeMetadata of a specified entityCeilingType
//     * this method, may take advantage of method: getEntityClassTree(...)
//     *
//     * @param entityCeilingType, the root entity-type of required ClassTreeMetadata
//     * @return
//     */
//    ClassTreeMetadata getClassTreeMetadata(Class<?> entityCeilingType);

    /**
     * Get ClassMetadata of a specified entityType
     *
     * @param entityType, the entity-type of required ClassMetadata
     * @return
     */
    ClassMetadata getClassMetadata(Class<?> entityType, boolean withHierarchy);

    ClassTreeMetadata getClassTreeMetadata(Class<?> entityCeilingType);

    /**
     * Get the entityInfo for a specified entityType
     *
     * @param entityType, specify the entity-type
     * @param withHierarchy, specify if the result should include information of the hierarchy-types
     * @param locale, the locale, if null, the information won't be translated
     * @param infoType, the infotype tobe returned
     * @return
     */
    IEntityInfo getEntityInfo(Class<?> entityType, boolean withHierarchy, Locale locale, EntityInfoType infoType);
}
