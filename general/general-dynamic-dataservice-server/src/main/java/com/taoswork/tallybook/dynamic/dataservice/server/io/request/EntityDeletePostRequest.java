package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityDeletePostRequest extends EntityInstancePostRequest {
    protected String id;
    public EntityDeletePostRequest(Entity entity) {
        super(entity);
    }

    public String getId() {
        return id;
    }

    public EntityDeletePostRequest setId(String id) {
        this.id = id;
        return this;
    }
}
