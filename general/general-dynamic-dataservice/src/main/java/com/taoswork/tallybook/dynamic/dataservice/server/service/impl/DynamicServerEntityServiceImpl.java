package com.taoswork.tallybook.dynamic.dataservice.server.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.request.RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class DynamicServerEntityServiceImpl implements DynamicServerEntityService {

    @Resource(name = DynamicEntityService.COMPONENT_NAME)
    DynamicEntityService dynamicEntityService;

    @Resource(name = EntityDescriptionService.SERVICE_NAME)
    EntityDescriptionService entityDescriptionService;

    @Override
    public EntityQueryResponse getGridRecords(EntityQueryRequest request){
        Class<?> entityType = request.getEntityType();

        CriteriaTransferObject cto = RequestTranslator.translate(request);
        CriteriaQueryResult<?> data = dynamicEntityService.query(entityType, cto);
        ClassTreeMetadata classTreeMetadata = dynamicEntityService.inspect(entityType);
        ClassEdo classEdo = entityDescriptionService.getClassEdo(classTreeMetadata);

        return ResponseTranslator.translate(request, data, classEdo);
    }
}
