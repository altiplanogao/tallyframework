package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.request;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public class RequestTranslator {
    public static CriteriaTransferObject translate(EntityQueryRequest request){
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.setFirstResult(request.getFirstResult());
        cto.setPageSize(request.getPageSize());
        cto.addFilterCriterias(request.getFilterCriterias());
        cto.addSortCriterias(request.getSortCriterias());

        return cto;
    }
}
