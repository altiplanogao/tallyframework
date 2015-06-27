package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.EntityInfoTypes;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Entity;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryListGridResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.EntityMaker;

/**
 * Created by Gao Yuan on 2015/6/19.
 */
public class ResponseTranslator {
    public static EntityQueryResponse translate(EntityQueryRequest request,
                                                CriteriaQueryResult<?> criteriaResult,
                                                EntityInfo entityInfo,
                                                EntityGridInfo entityGridInfo,
                                                EntityFormInfo entityFormInfo){
        EntityQueryResponse response = new EntityQueryResponse()
                .setEntities(criteriaResult.getEntityCollection());
        if(null != entityInfo){
            response.addEntityInfo(EntityInfoTypes.ENTITY_INFO_TYPE_FULL, entityInfo);
        }
        if(null != entityGridInfo){
            response.addEntityInfo(EntityInfoTypes.ENTITY_INFO_TYPE_GRID, entityGridInfo);
        }
        if(null!= entityFormInfo){
            response.addEntityInfo(EntityInfoTypes.ENTITY_INFO_TYPE_FORM, entityFormInfo);
        }
        response.setStartIndex(request.getFirstResult())
                .setPageSize(request.getPageSize())
                .setTotalCount(criteriaResult.getTotalCount());
        return response;
    }

    public static EntityQueryListGridResponse translate(EntityQueryResponse rawResponse){
        EntityGridInfo entityGridInfo = rawResponse.getEntityInfo(EntityInfoTypes.ENTITY_INFO_TYPE_GRID);
        EntityQueryListGridResponse response = new EntityQueryListGridResponse()
                .setEntities(EntityMaker.makeGridEntityList(rawResponse.getEntities(), entityGridInfo));

        response.addEntityInfos(rawResponse.getEntityInfos());

        response.setStartIndex(rawResponse.getStartIndex())
                .setPageSize(rawResponse.getPageSize())
                .setTotalCount(rawResponse.getTotalCount());
        return response;
    }
}
