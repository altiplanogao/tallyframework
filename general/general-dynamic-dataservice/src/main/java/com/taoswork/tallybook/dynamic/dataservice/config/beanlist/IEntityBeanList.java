package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationService;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
public interface IEntityBeanList {

    EntityValidationService entityValidatorService();

    DynamicEntityPersistenceService dynamicEntityPersistenceService();

    DynamicEntityService dynamicEntityService();
}
