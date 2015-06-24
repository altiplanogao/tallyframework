package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
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
                                                ClassEdo classEdo){
        EntityQueryResponse response = new EntityQueryResponse()
                .setEntities(criteriaResult.getEntityCollection())
                .setClassEdo(classEdo);
        response.setStartIndex(request.getFirstResult())
                .setPageSize(request.getPageSize())
                .setTotalCount(criteriaResult.getTotalCount());
        return response;
    }

    public static EntityQueryListGridResponse translate(EntityQueryResponse rawResponse){
        EntityQueryListGridResponse response = new EntityQueryListGridResponse()
                .setEntities(EntityMaker.makeGridEntityList(rawResponse.getEntities(), rawResponse.getClassEdo()))
                .setClassEdo(rawResponse.getClassEdo());
        response.setStartIndex(rawResponse.getStartIndex())
                .setPageSize(rawResponse.getPageSize())
                .setTotalCount(rawResponse.getTotalCount());
        return response;
    }
}
