package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
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
                .setEntities(EntityMaker.makeGridEntityList(criteriaResult.getEntityCollection(), classEdo))
                .setStartIndex(request.getFirstResult())
                .setTotalCount(criteriaResult.getTotalCount())
                .setClassEdo(classEdo);
        return response;

    }
}
