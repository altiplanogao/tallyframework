package com.taoswork.tallybook.dataservice.config.beanlist;

import com.taoswork.tallybook.dataservice.service.EntityCopierService;
import com.taoswork.tallybook.dataservice.service.EntityValidationService;
import com.taoswork.tallybook.dataservice.service.EntityValueGateService;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface IEntityProtectionBeanList {
    EntityValidationService entityValidatorService();

    EntityValueGateService entityValueGateService();

}
