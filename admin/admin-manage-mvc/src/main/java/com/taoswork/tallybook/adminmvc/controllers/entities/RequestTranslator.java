package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.taoswork.tallybook.dynamic.dataservice.server.dto.request.EntityQueryRequest;
import org.springframework.util.MultiValueMap;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class RequestTranslator {
    public static EntityQueryRequest makeQueryRequest(
            String entityClz,
            MultiValueMap<String, String> requestParams) {
        EntityQueryRequest request =  new EntityQueryRequest();
        request.withEntityType(entityClz);
        return request;
    }
}
