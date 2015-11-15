package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataio.in.Entity;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityUpdateRequest extends EntityInstancePostRequest {
    public EntityUpdateRequest(Entity entity) {
        super(entity);
    }
}
