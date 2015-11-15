package com.taoswork.tallybook.dynamic.datameta.metadata;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public interface IClassMetadataAccess {
    /**
     * Get IClassMetadata of a specified entityType
     *
     * @param entityType, the entity-type of required IClassMetadata
     * @return
     */
    IClassMetadata getClassMetadata(Class<?> entityType, boolean withHierarchy);

    IClassMetadata getClassTreeMetadata(Class<?> entityCeilingType);
}
