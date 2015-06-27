package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface DynamicServerEntityService {
    public static final String SERVICE_NAME = "DynamicServerEntityService";

    EntityQueryResponse getQueryRecords(EntityQueryRequest request);

    EntityInfo inspect(Class<?> entityType);

    EntityGridInfo inspectForGrid(Class<?> entityType);

    EntityFormInfo inspectForForm(Class<?> entityType);
}
