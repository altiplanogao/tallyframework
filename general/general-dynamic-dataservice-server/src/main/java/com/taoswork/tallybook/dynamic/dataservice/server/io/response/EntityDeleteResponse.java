package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityDeleteResponse extends EntityResponse {

    @Override
    public String getAction() {
        return EntityActionNames.DELETE;
    }

    //TODO: use EntityDeleteResult instead
    boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public EntityDeleteResponse setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
