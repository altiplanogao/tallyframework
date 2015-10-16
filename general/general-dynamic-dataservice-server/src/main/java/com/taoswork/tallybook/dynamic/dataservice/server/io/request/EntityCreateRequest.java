package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityCreateRequest extends EntityInstancePostRequest {
    public EntityCreateRequest(Entity entity) {
        super(entity);
    }
}
