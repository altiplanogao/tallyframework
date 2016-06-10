package com.taoswork.tallybook.dataservice.server.io.response;

import com.taoswork.tallybook.datadomain.base.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityDeleteResponse extends EntityResponse {
    private final String beanUri;

    //TODO: use EntityDeleteResult instead
    boolean deleted = false;

    public EntityDeleteResponse(String beanUri) {
        this.beanUri = beanUri;
    }

    public String getBeanUri() {
        return beanUri;
    }

    @Override
    public String getAction() {
        return EntityAction.DELETE.getType();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public EntityDeleteResponse setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
