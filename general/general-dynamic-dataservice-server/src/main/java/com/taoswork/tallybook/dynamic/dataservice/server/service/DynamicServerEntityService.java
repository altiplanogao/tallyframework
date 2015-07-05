package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface DynamicServerEntityService {
    public static final String SERVICE_NAME = "DynamicServerEntityService";

    EntityQueryResponse getQueryRecords(EntityQueryRequest request);

    IEntityInfo getEntityInfo(Class<?> entityType, EntityInfoType infoType);

    // IEntityInfo getFriendlyEntityInfo(Class<?> entityType, EntityInfoType infoType, Locale locale);
}
