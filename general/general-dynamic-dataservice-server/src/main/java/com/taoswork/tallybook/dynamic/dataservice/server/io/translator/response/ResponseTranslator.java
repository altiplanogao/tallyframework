package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityAddGetRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityQueryResult;

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

    public static EntityQueryResponse translateQueryResponse(EntityQueryRequest request,
                                                             CriteriaQueryResult<?> criteriaResult){
        EntityQueryResponse response = new EntityQueryResponse();
        translate(request, response);
        EntityQueryResult queryResult = ResultTranslator.convertQueryResult(request, criteriaResult);
        response.setEntities(queryResult);
        return response;
    }

    public static EntityReadResponse translateReadResponse(EntityReadRequest request,
                                                           Object data){
        EntityReadResponse response = new EntityReadResponse();
        translateInstanceResponse(request, data, response);
        return response;
    }

    public static EntityAddGetResponse translateAddGetResponse(EntityAddGetRequest request,
                                                           Object data){
        EntityAddGetResponse response = new EntityAddGetResponse();
        translateInstanceResponse(request, data, response);
        return response;
    }

    private static void translateInstanceResponse(EntityRequest request,
                                                               Object data, EntityInstanceResponse response){
        translate(request, response);
        EntityInstanceResult readResult = ResultTranslator.convertInstanceResult(data);
        response.setEntity(readResult);
    }
}
