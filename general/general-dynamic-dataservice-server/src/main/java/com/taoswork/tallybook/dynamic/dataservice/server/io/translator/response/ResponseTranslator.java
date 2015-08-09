package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityReadResult;

/**
 * Created by Gao Yuan on 2015/6/19.
 */
public class ResponseTranslator {
    public static void translate(
        EntityRequest request,
        EntityResponse response){
        response.setResourceName(request.getResourceName())
            .setEntityType(request.getEntityType())
            .setBaseUrl(request.getResourceURI());
    }

    public static EntityQueryResponse translateQueryResponse(
            EntityQueryRequest request,
            CriteriaQueryResult<?> criteriaResult){
        EntityQueryResponse response = new EntityQueryResponse();
        translate(request, response);
        EntityQueryResult queryResult = ResultTranslator.convertQueryResult(request, criteriaResult);
        response.setEntities(queryResult);
        return response;
    }

    public static EntityReadResponse translateReadResponse(
        EntityReadRequest request,
        Object data){
        EntityReadResponse response = new EntityReadResponse();
        translate(request, response);
        EntityReadResult readResult = ResultTranslator.convertReadResult(request, data);
        response.setEntity(readResult);
        return response;
    }
}
