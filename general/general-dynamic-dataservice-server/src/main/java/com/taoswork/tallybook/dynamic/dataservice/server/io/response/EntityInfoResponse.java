package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;

/**
 * Created by Gao Yuan on 2015/9/30.
 */
public class EntityInfoResponse extends EntityResponse {
    @Override
    public String getAction() {
        return EntityActionNames.INSPECT;
    }
}