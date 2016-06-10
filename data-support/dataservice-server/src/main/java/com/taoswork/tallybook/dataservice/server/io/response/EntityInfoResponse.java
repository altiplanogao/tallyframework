package com.taoswork.tallybook.dataservice.server.io.response;

import com.taoswork.tallybook.datadomain.base.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/9/30.
 */
public class EntityInfoResponse extends EntityResponse {
    @Override
    public String getAction() {
        return EntityAction.INFO.getType();
    }
}
