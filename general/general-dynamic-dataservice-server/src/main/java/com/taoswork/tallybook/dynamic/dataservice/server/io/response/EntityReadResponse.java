package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityReadResult;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityReadResponse extends EntityResponse {
    EntityReadResult entity;

    public EntityReadResult getEntity() {
        return entity;
    }

    public EntityReadResponse setEntity(EntityReadResult entity) {
        this.entity = entity;
        return this;
    }
}
