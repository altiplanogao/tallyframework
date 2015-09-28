package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
public interface IEntityBeanList {
    DynamicEntityPersistenceService dynamicEntityPersistenceService();

    DynamicEntityService dynamicEntityService();
}
