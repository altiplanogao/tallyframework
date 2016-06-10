package com.taoswork.tallybook.dataservice.server.service;

import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityErrors;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public interface IServiceExceptionTranslator {
    void translate(ServiceException serviceException, EntityErrors errors);
}
