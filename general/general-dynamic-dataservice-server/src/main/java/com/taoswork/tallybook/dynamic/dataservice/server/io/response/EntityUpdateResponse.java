package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.datadomain.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityUpdateResponse extends EntityInstanceResponse {
    private final String beanUri;

    public EntityUpdateResponse(String beanUri) {
        this.beanUri = beanUri;
    }

    public String getBeanUri() {
        return beanUri;
    }

    @Override
    public String getAction() {
        return EntityAction.UPDATE.getType();
    }
}
