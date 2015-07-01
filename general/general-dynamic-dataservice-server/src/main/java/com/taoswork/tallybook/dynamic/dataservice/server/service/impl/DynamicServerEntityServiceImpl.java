package com.taoswork.tallybook.dynamic.dataservice.server.service.impl;

import com.taoswork.tallybook.dynamic.datameta.description.service.MetaDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoTypes;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.request.RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class DynamicServerEntityServiceImpl implements DynamicServerEntityService {

    @Resource(name = DynamicEntityService.COMPONENT_NAME)
    DynamicEntityService dynamicEntityService;

    @Resource(name = MetaDescriptionService.SERVICE_NAME)
    MetaDescriptionService metaDescriptionService;

    @Override
    public EntityQueryResponse getQueryRecords(EntityQueryRequest request) {
        Class<?> entityType = request.getEntityType();

        CriteriaTransferObject cto = RequestTranslator.translate(request);
        CriteriaQueryResult<?> data = dynamicEntityService.query(entityType, cto);
        ClassTreeMetadata classTreeMetadata = dynamicEntityService.inspect(entityType);
        EntityInfo entityInfo = null;
        EntityGridInfo entityGridInfo = null;
        EntityFormInfo entityFormInfo = null;
        if (request.hasEntityInfoName(EntityInfoTypes.ENTITY_INFO_TYPE_FULL)) {
            entityInfo = metaDescriptionService.getEntityInfo(classTreeMetadata);
        }
        if (request.hasEntityInfoName(EntityInfoTypes.ENTITY_INFO_TYPE_GRID)) {
            entityGridInfo = metaDescriptionService.getEntityGridInfo(classTreeMetadata);
        }
        if (request.hasEntityInfoName(EntityInfoTypes.ENTITY_INFO_TYPE_FORM)) {
            entityFormInfo = metaDescriptionService.getEntityFormInfo(classTreeMetadata);
        }

        return ResponseTranslator.translate(request, data, entityInfo, entityGridInfo, entityFormInfo);
    }

    @Override
    public EntityInfo inspect(Class<?> entityType) {
        ClassTreeMetadata classTreeMetadata = dynamicEntityService.inspect(entityType);
        EntityInfo classInfo = metaDescriptionService.getEntityInfo(classTreeMetadata);
        return classInfo;
    }

    @Override
    public EntityGridInfo inspectForGrid(Class<?> entityType) {
        ClassTreeMetadata classTreeMetadata = dynamicEntityService.inspect(entityType);
        EntityGridInfo classGridInfo = metaDescriptionService.getEntityGridInfo(classTreeMetadata);
        return classGridInfo;
    }

    @Override
    public EntityFormInfo inspectForForm(Class<?> entityType) {
        ClassTreeMetadata classTreeMetadata = dynamicEntityService.inspect(entityType);
        EntityFormInfo classFormInfo = metaDescriptionService.getEntityFormInfo(classTreeMetadata);
        return classFormInfo;
    }
}
