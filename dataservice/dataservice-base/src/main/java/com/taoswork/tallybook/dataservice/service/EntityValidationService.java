package com.taoswork.tallybook.dataservice.service;

import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface EntityValidationService {
    public final static String COMPONENT_NAME = "EntityValidationService";

    void validate(PersistableResult entity) throws ServiceException;
}
