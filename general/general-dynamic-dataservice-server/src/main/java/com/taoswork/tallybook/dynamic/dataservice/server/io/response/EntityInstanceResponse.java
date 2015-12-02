package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public abstract class EntityInstanceResponse extends EntityResponse {

    EntityInstanceResult entity;

    public EntityInstanceResult getEntity() {
        return entity;
    }

    public EntityInstanceResponse setEntity(EntityInstanceResult entity) {
        this.entity = entity;
        return this;
    }
}
