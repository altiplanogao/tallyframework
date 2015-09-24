package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface EntityMetadataRawAccess {

    Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz);

    Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz, boolean includeNotInstanceable);

    Field getIdField(Class<?> entityClass);

    List<String> getPropertyNames(Class<?> entityClass);

    Class<?>[] getAllEntityClasses();
}
