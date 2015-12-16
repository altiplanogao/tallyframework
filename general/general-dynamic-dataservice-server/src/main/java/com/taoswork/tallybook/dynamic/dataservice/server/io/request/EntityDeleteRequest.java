package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter.EntityTypeParameter;

import java.net.URI;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityDeleteRequest extends EntityInstancePostRequest {
    protected String id;

    public EntityDeleteRequest(EntityTypeParameter entityTypeParam, URI fullUri, Entity entity) {
        super(entityTypeParam, fullUri, entity);
    }

    public EntityDeleteRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        super(entityTypeParam, fullUri);
    }

    public String getId() {
        return id;
    }

    public EntityDeleteRequest setId(String id) {
        this.id = id;
        return this;
    }
}
