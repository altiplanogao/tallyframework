package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface EntityMetadataRawAccess {

    Class<?>[] getAllEntities();

    Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz);

    Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz, boolean includeNotInstantiable);

    Field getIdField(Class<?> entityClass);

    List<String> getPropertyNames(Class<?> entityClass);
}
