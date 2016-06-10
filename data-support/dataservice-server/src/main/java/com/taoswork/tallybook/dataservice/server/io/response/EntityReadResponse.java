package com.taoswork.tallybook.dataservice.server.io.response;

import com.taoswork.tallybook.datadomain.base.restful.EntityAction;

public class EntityReadResponse extends EntityInstanceResponse {
    private final String beanUri;

    public EntityReadResponse(String beanUri) {
        this.beanUri = beanUri;
    }

    public String getBeanUri() {
        return beanUri;
    }

    @Override
    public String getAction() {
        return EntityAction.READ.getType();
    }

    @Override
    public boolean success() {
        return super.success();
    }

    public boolean gotRecord() {
        return (getEntity() != null && getEntity().getBean() != null);
    }
}
