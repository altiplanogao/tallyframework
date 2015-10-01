package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityErrors;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public interface IServiceExceptionTranslator {
    void translate(ServiceException serviceException, EntityErrors errors);
}
