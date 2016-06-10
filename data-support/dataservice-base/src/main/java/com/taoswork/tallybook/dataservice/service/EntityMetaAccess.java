package com.taoswork.tallybook.dataservice.service;

import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;

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
public interface EntityMetaAccess extends IClassMetaAccess {
    public static final String COMPONENT_NAME = "EntityMetaAccess";

    /**
     * Returns all the instance-able entity-types, interfaces
     *
     * @param _entity, if true, Returns all the instance-able entity-types
     * @param _interface, if true. Returns all the interfaces that referenced by instance-able entity-types
     * @return
     */
    Collection<Class> getAllEntities(boolean _entity, boolean _interface);

    /**
     * Get the root instance-able entity-type
     * @param entityCeilingType
     * @param <T>
     * @return
     */
    <T> Class<T> getRootInstantiableEntityType(Class<T> entityCeilingType);

    /**
     * Get all the instance-able entity-types derived from entityCeilingType
     * @param entityCeilingType, the super entity-type of required instance-able entity-types
     * @return
     */
    Collection<Class> getInstantiableEntityTypes(Class<?> entityCeilingType);

    <T> Class<T> getPermissionGuardian(Class<T> entityType);

    /**
     * Get a class tree with root (root's type is entityCeilingType)
     * @param entityCeilingType, the root entity-type of required EntityClassTree
     * @return
     */
    EntityClassTree getEntityClassTree(Class<?> entityCeilingType);

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
