package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
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
            .setEntityCeilingType(request.getEntityType())
            .setEntityType(request.getEntityType())
            .setBaseUrl(request.getResourceURI());
    }

    public static EntityQueryResponse translateQueryResponse(EntityQueryRequest request,
                                                             CriteriaQueryResult<?> criteriaResult){
        EntityQueryResponse response = new EntityQueryResponse();
        translate(request, response);
        EntityQueryResult queryResult = ResultTranslator.convertQueryResult(request, criteriaResult);
        response.setEntityType(criteriaResult.getEntityType());
        response.setEntities(queryResult);
        return response;
    }

    public static EntityReadResponse translateReadResponse(EntityReadRequest request,
                                                           EntityResult result){
        EntityReadResponse response = new EntityReadResponse();
        translateInstanceResponse(request, result, response);
        return response;
    }

    public static EntityUpdatePostResponse translateUpdatePostResponse(EntityUpdatePostRequest request,
                                                                       EntityResult result) {
        EntityUpdatePostResponse response = new EntityUpdatePostResponse();
        translateInstanceResponse(request, result, response);
        return response;
    }

    public static EntityAddPostResponse translateAddPostResponse(EntityAddPostRequest request,
                                                                 EntityResult result) {
        EntityAddPostResponse response = new EntityAddPostResponse();
        translateInstanceResponse(request, result, response);
        return response;
    }

    public static EntityAddGetResponse translateAddGetResponse(EntityAddGetRequest request,
                                                               EntityResult result){
        EntityAddGetResponse response = new EntityAddGetResponse();
        translateInstanceResponse(request, result, response);
        return response;
    }

    private static void translateInstanceResponse(EntityRequest request,
                                                  EntityResult result, EntityInstanceResponse response){
        translate(request, response);
        if(result == null){
            throw new IllegalArgumentException();
        }
        Object resultEntity = result.getEntity();
        response.setEntityType(resultEntity.getClass());
        EntityInstanceResult instanceResult = ResultTranslator.convertInstanceResult(result);
        response.setEntity(instanceResult);
    }

    public static EntityDeletePostResponse translateDeletePostResponse(EntityDeletePostRequest request, boolean deleted) {
        EntityDeletePostResponse response = new EntityDeletePostResponse();
        translate(request, response);
        response.setSuccess(deleted);
        return response;
    }
}
