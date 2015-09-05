package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInfoResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityQueryResult;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class ResultTranslator {
    public static EntityInfoResult convertEntityInfoResult(EntityRequest request){
        EntityInfoResult infoResult = new EntityInfoResult();
        infoResult.setResourceName(request.getResourceName())
            .setEntityType(request.getEntityType())
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

    public static EntityInstanceResult convertReadResult(EntityReadRequest request, Object data) {
        EntityInstanceResult result = new EntityInstanceResult();
        result.setData(data);
        return result;
    }
}
