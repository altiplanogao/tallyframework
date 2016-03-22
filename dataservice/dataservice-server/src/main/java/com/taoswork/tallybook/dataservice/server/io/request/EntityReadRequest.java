package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;

import java.net.URI;

public class EntityReadRequest extends EntityRequest {

    private String id;

    public EntityReadRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        super(entityTypeParam, fullUri);
    }

    public String getId() {
        return id;
    }

    public EntityReadRequest setId(String id) {
        this.id = (id);
        return this;
    }

}
