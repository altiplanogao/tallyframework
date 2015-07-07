package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoTypeNames;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityInfoResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryListGridResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.ResponseParameter;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.EntityMaker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/19.
 */
public class ResponseTranslator {
    public static EntityQueryResponse translate(
            EntityQueryRequest request,
            CriteriaQueryResult<?> criteriaResult){
        EntityQueryResponse response = new EntityQueryResponse()
                .setDetails(criteriaResult.getEntityCollection());
        response.setResourceName(request.getResourceName());
        response.setStartIndex(request.getFirstResult())
                .setPageSize(request.getPageSize())
                .setTotalCount(criteriaResult.getTotalCount());
        return response;
    }

    public static EntityInfoResponse translate(
            EntityQueryRequest request,
            IEntityInfo[] entityInfos){
        EntityInfoResponse response = new EntityInfoResponse(request.getEntityType(), request.getResourceName());
        for (IEntityInfo entityInfo : entityInfos){
            if(entityInfo != null){
                response.addDetail(entityInfo.getInfoType(), entityInfo);
            }
        }
        return response;
    }

    public static EntityQueryListGridResponse translate(
            EntityQueryResponse rawResponse,
            EntityInfoResponse infoResponse){
        EntityGridInfo entityGridInfo = infoResponse.getDetail(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_GRID);
        EntityQueryListGridResponse response = new EntityQueryListGridResponse()
                .setEntities(EntityMaker.makeGridEntityList(rawResponse.getDetails(), entityGridInfo));

        response.setStartIndex(rawResponse.getStartIndex())
                .setPageSize(rawResponse.getPageSize())
                .setTotalCount(rawResponse.getTotalCount());
        return response;
    }

    public static Map<String, Object> mergeResult(
            Map<String, Object> result,
            EntityQueryResponse queryResponse,
            EntityInfoResponse infoResponse, Object links){
        if(result == null) {
            result = new HashMap<String, Object>();
        }
        if(queryResponse != null){
            result.put(ResponseParameter.ENTITIES_KEY, queryResponse);
        }
        if(infoResponse != null){
            result.put(ResponseParameter.ENTITIES_INFOS, infoResponse);
        }
        if(links != null){
            result.put(ResponseParameter.LINKS, links);
        }
        return result;
    }
}
