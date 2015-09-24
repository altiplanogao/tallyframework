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
    public static EntityInfoResult convertEntityInfoResult(EntityRequest request, EntityResponse response){
        EntityInfoResult infoResult = new EntityInfoResult();
        infoResult.setResourceName(request.getResourceName())
            .setEntityCeilingType(request.getEntityType())
            .setEntityType(response.getEntityType())
            .setBaseUrl(request.getResourceURI());
        return infoResult;
    }

    public static EntityQueryResult convertQueryResult(EntityQueryRequest request,
                                 CriteriaQueryResult<?> criteriaResult){
        EntityQueryResult result = new EntityQueryResult();
        result.setStartIndex(request.getStartIndex())
            .setPageSize(request.getPageSize())
            .setTotalCount(criteriaResult.getTotalCount());
        result.setRecords(criteriaResult.getEntityCollection());
        return result;
    }

    public static EntityInstanceResult convertInstanceResult(EntityResult er) {
        EntityInstanceResult result = new EntityInstanceResult();
        result.setData(er.getEntity());
        result.setIdKey(er.getIdKey());
        result.setIdValue(er.getIdValue());
        return result;
    }
}
