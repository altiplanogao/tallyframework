package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect;

import com.taoswork.tallybook.dynamic.dataservice.core.dataio.PersistableResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface EntityValidationService {
    public final static String COMPONENT_NAME = "EntityValidationService";

    void validate(PersistableResult entity) throws ServiceException;
}