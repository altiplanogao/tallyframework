package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter.EntityTypeParameter;

import java.net.URI;

public class EntityReadRequest extends EntityRequest {

    private long id;

    public EntityReadRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        super(entityTypeParam, fullUri);
    }

    public long getId() {
        return id;
    }

    public EntityReadRequest setId(String id) {
        this.id = Long.parseLong(id);
        return this;
    }

    public EntityReadRequest setId(long id) {
        this.id = id;
        return this;
    }
}
