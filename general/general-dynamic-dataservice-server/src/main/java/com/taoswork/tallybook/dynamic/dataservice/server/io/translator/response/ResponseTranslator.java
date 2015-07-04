package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoTypeNames;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
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
                                                IEntityInfo[] entityInfos){
        EntityQueryResponse response = new EntityQueryResponse()
                .setEntities(criteriaResult.getEntityCollection());
        for (IEntityInfo entityInfo : entityInfos){
            if(entityInfo != null){
                response.addInfo(entityInfo.getInfoType(), entityInfo);
            }
        }
        response.setStartIndex(request.getFirstResult())
                .setPageSize(request.getPageSize())
                .setTotalCount(criteriaResult.getTotalCount());
        return response;
    }

    public static EntityQueryListGridResponse translate(EntityQueryResponse rawResponse){
        EntityGridInfo entityGridInfo = rawResponse.getInfo(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_GRID);
        EntityQueryListGridResponse response = new EntityQueryListGridResponse()
                .setEntities(EntityMaker.makeGridEntityList(rawResponse.getEntities(), entityGridInfo));

        response.addInfos(rawResponse.getInfos());

        response.setStartIndex(rawResponse.getStartIndex())
                .setPageSize(rawResponse.getPageSize())
                .setTotalCount(rawResponse.getTotalCount());
        return response;
    }
}
