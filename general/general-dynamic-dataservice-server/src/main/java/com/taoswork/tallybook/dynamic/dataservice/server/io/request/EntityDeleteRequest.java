package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityDeleteRequest extends EntityInstancePostRequest {
    protected String id;
    public EntityDeleteRequest(Entity entity) {
        super(entity);
    }

    public String getId() {
        return id;
    }

    public EntityDeleteRequest setId(String id) {
        this.id = id;
        return this;
    }
}
