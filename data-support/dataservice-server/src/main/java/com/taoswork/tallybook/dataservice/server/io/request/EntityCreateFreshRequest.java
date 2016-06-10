package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;

import java.net.URI;

public class EntityCreateFreshRequest extends EntityRequest {
    public EntityCreateFreshRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        super(entityTypeParam, fullUri);
    }
}
