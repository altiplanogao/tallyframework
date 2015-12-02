package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;

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
        return EntityActionNames.READ;
    }

    @Override
    public boolean success() {
        return super.success();
    }

    public boolean gotRecord(){
        return (getEntity() != null && getEntity().getBean() != null);
    }
}
