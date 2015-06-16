package com.taoswork.tallybook.dynamic.dataservice.server.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.service.translate.RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.service.utils.EntityMaker;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class DynamicServerEntityServiceImpl implements DynamicServerEntityService {

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Resource(name = DynamicEntityService.COMPONENT_NAME)
    DynamicEntityService dynamicEntityService;

    @Resource(name = EntityMetadataService.SERVICE_NAME)
    EntityMetadataService entityMetadataService;

    @Resource(name = EntityDescriptionService.SERVICE_NAME)
    EntityDescriptionService entityDescriptionService;

    @Override
    public EntityQueryResponse getGridRecords(EntityQueryRequest request){
        Class<?> entityType = request.getEntityType();

        Class<?> rootPersistiveClz = dynamicEntityService.getRootPersistiveEntityClass(entityType);
        ClassTreeMetadata classTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(rootPersistiveClz);
        ClassEdo classEdo = entityDescriptionService.getClassEdo(classTreeMetadata);
        CriteriaTransferObject cto = RequestTranslator.translate(request);
        List<?> data = dynamicEntityService.query(entityType, cto);
        EntityQueryResponse response = new EntityQueryResponse();

        response.setEntities(EntityMaker.makeGridEntityList(data, classEdo));
        response.setClassEdo(classEdo);
        return response;
    }
}
