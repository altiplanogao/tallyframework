package com.taoswork.tallybook.dynamic.dataservice.core.dataio;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;

import java.util.Collection;
import java.util.Map;

public interface IEntityRecordsFetcher {
    Map<Object,Object> fetch(Class entityType, Collection<Object> ids) throws ServiceException;
}
