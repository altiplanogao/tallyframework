package com.taoswork.tallybook.dataservice.core.metaaccess.helper;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public interface EntityMetaRawAccess {

    Class<?>[] getAllEntities();
//
//    Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz, boolean includeNotInstantiable);

    Field getIdField(Class<?> entityClass);
}
