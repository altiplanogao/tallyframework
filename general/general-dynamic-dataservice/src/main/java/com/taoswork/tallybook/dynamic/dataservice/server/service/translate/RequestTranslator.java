package com.taoswork.tallybook.dynamic.dataservice.server.service.translate;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.request.EntityQueryRequest;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public class RequestTranslator {
    public static CriteriaTransferObject translate(EntityQueryRequest request){
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.setFirstResult(request.getFirstResult());
        cto.setMaxResultCount(request.getMaxResultCount());
        cto.addFilterCriterias(request.getFilterCriterias());
        cto.addSortCriterias(request.getSortCriterias());

        return cto;
    }
}
