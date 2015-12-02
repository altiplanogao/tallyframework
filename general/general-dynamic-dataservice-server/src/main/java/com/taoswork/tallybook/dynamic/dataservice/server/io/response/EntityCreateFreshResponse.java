package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityCreateFreshResponse extends EntityInstanceResponse {
    private final String createUri;

    public EntityCreateFreshResponse(String createUri) {
        this.createUri = createUri;
    }

    public String getCreateUri() {
        return createUri;
    }

    @Override
    public String getAction() {
        return EntityActionNames.CREATE;
    }
}
