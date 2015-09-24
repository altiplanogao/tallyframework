package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;

abstract class EntityInstancePostRequest extends EntityRequest{

    private Entity entity;

    public EntityInstancePostRequest(){
        this(null);
    }

    public EntityInstancePostRequest(Entity entity) {
        this.entity = CloneUtility.makeClone(entity);
    }

    public EntityInstancePostRequest setEntity(Entity entity) {
        this.entity = CloneUtility.makeClone(entity);
        return this;
    }

    public Entity getEntity() {
        return entity;
    }
}