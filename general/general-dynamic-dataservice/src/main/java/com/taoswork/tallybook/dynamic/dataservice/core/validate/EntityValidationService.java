package com.taoswork.tallybook.dynamic.dataservice.core.validate;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface EntityValidationService {
    public final static String COMPONENT_NAME = "EntityValidationService";

    void validate(EntityResult entity) throws ServiceException;
}
