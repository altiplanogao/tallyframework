package com.taoswork.tallybook.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dataservice.server.io.request.EntityRequest;
import com.taoswork.tallybook.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityInfoResult;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityQueryResult;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class ResultTranslator {
    public static EntityInfoResult convertInfoResult(EntityRequest request,
                                                     EntityResponse response) {
        EntityInfoResult result = new EntityInfoResult();
        result.setCeilingType(request.getEntityType())
                .setType(response.getEntityType())
                .setBeanUri(LinkBuilder.buildLinkForReadInstance(request.getResourceName()));
        return result;
    }

    public static EntityQueryResult convertQueryResult(EntityQueryRequest request,
                                                       CriteriaQueryResult<?> criteriaResult) {
        EntityQueryResult result = new EntityQueryResult();
        result.setStartIndex(request.getStartIndex())
                .setPageSize(request.getPageSize());
        if (criteriaResult != null) {
            result.setTotalCount(criteriaResult.getTotalCount())
                    .setBeans(criteriaResult.getEntityCollection());
        }
        return result;
    }

    public static EntityInstanceResult convertInstanceResult(PersistableResult er) {
        EntityInstanceResult result = new EntityInstanceResult();
        result.setBean(er.getValue())
                .setName(er.getName())
                .setIdKey(er.getIdKey())
                .setIdValue(er.getIdValue());
        return result;
    }
}
