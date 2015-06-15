package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataservice.server.dto.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.response.EntityQueryResponse;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface DynamicServerEntityService {
    public static final String SERVICE_NAME = "DynamicServerEntityService";

    EntityQueryResponse getGridRecords(EntityQueryRequest request);
}
