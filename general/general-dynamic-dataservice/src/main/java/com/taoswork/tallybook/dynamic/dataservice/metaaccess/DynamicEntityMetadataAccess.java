package com.taoswork.tallybook.dynamic.dataservice.metaaccess;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/28.
 */
public interface DynamicEntityMetadataAccess {
    public static final String COMPONENT_NAME = "DynamicEntityMetadataAccess";

    Map<String, EntityClassTree> getAllEntityClassTree();

    <T> Class<T> getRootPersistiveEntityClass(Class<T> entityClz);

    EntityClassTree getEntityClassTree(Class<?> entityClz);

    ClassTreeMetadata getClassTreeMetadata(Class<?> entityClz);
}
