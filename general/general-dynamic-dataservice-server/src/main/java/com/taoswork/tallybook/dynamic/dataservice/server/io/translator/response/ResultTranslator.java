package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInfoResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityQueryResult;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class ResultTranslator {
    public static EntityInfoResult convertInfoResult(EntityRequest request,
                                                     EntityResponse response){
        EntityInfoResult result = new EntityInfoResult();
        result.setResourceName(request.getResourceName())
            .setEntityCeilingType(request.getEntityType())
            .setEntityType(response.getEntityType())
            .setBaseUrl(request.getResourceURI());
        return result;
    }

    public static EntityQueryResult convertQueryResult(EntityQueryRequest request,
                                                       CriteriaQueryResult<?> criteriaResult){
        EntityQueryResult result = new EntityQueryResult();
        result.setStartIndex(request.getStartIndex())
            .setPageSize(request.getPageSize());
        if(criteriaResult != null) {
            result.setTotalCount(criteriaResult.getTotalCount())
                .setRecords(criteriaResult.getEntityCollection());
        }
        return result;
    }

    public static EntityInstanceResult convertInstanceResult(EntityResult er) {
        EntityInstanceResult result = new EntityInstanceResult();
        result.setData(er.getEntity())
            .setIdKey(er.getIdKey())
            .setIdValue(er.getIdValue());
        return result;
    }
}
