package com.taoswork.tallybook.dynamic.dataservice.metaaccess.helper;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface EntityMetadataRawAccess {

    Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClz);

    Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClz, boolean includeUnqualifiedPolymorphicEntities);

    Map<String, String> getIdMetadata(Class<?> entityClass);

    List<String> getPropertyNames(Class<?> entityClass);

 //   List<Type> getPropertyTypes(Class<?> entityClass);

    Class<?>[] getAllEntityClasses();
}
