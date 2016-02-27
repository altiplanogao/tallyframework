package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;

import java.net.URI;

public abstract class EntityInstancePostRequest extends EntityRequest {

    private Entity entity;

    public EntityInstancePostRequest(EntityTypeParameter entityTypeParam, URI fullUri, Entity entity) {
        super(entityTypeParam, fullUri);
        this.entity = CloneUtility.makeClone(entity);
    }

    public EntityInstancePostRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        this(entityTypeParam, fullUri, null);
    }

    public EntityInstancePostRequest setEntity(Entity entity) {
        this.entity = CloneUtility.makeClone(entity);
        return this;
    }

    public Entity getEntity() {
        return entity;
    }
}
