package com.taoswork.tallybook.descriptor.metadata;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public interface IClassMetaAccess {
    /**
     * Get IClassMeta of a specified entityType
     *
     * @param entityType, the entity-type of required IClassMeta
     * @return
     */
    IClassMeta getClassMeta(Class<?> entityType, boolean withHierarchy);

    IClassMeta getClassTreeMeta(Class<?> entityCeilingType);
}
