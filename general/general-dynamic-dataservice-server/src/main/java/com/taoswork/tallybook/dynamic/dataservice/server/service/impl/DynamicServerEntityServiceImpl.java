package com.taoswork.tallybook.dynamic.dataservice.server.service.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoTypeNames;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.request.RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class DynamicServerEntityServiceImpl implements DynamicServerEntityService {

    @Resource(name = DynamicEntityService.COMPONENT_NAME)
    private DynamicEntityService dynamicEntityService;

//    @Resource(name = MetaInfoService.SERVICE_NAME)
//    private MetaInfoService metaDescriptionService;
//
//    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
//    private DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Override
    public EntityQueryResponse getQueryRecords(EntityQueryRequest request) {
        Class<?> entityType = request.getEntityType();

        CriteriaTransferObject cto = RequestTranslator.translate(request);
        CriteriaQueryResult<?> data = dynamicEntityService.query(entityType, cto);
        ClassTreeMetadata classTreeMetadata = dynamicEntityService.inspect(entityType);

        List<IEntityInfo> entityInfos = new ArrayList<IEntityInfo>();
        for (EntityInfoType infoType : request.getEntityInfoTypes()){
            IEntityInfo entityInfo = dynamicEntityService.describe(entityType, infoType);
            entityInfos.add(entityInfo);
        }

        return ResponseTranslator.translate(request, data, entityInfos.toArray(new IEntityInfo[3]));
    }
}
