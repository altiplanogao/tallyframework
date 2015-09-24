package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityAddPostRequest extends EntityInstancePostRequest {
    public EntityAddPostRequest(Entity entity) {
        super(entity);
    }
}
