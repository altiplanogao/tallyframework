package com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/28.
 */
public interface DynamicEntityMetadataAccess {
    public static final String COMPONENT_NAME = "DynamicEntityMetadataAccess";

    EntityClassTree getEntityClassTree(Class<?> entityClz);

    <T> Class<T> getRootPersistiveEntityClass(Class<T> entityClz);

    Map<String, EntityClassTree> getAllEntityClassTree();

    ClassEdo getMergedClassEdo(Class<?> entityClz);
}
