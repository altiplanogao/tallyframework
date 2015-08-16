package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface EntityMetadataRawAccess {

    Class<?>[] getAllInstanceableEntitiesFromCeiling(Class<?> ceilingClz);

    Class<?>[] getAllInstanceableEntitiesFromCeiling(Class<?> ceilingClz, boolean includeUnqualifiedPolymorphicEntities);

    Map<String, String> getIdMetadata(Class<?> entityClass);

    List<String> getPropertyNames(Class<?> entityClass);

    Class<?>[] getAllEntityClasses();
}
