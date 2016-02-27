package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;

import java.net.URI;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public class EntityInfoRequest extends EntityRequest {
    public EntityInfoRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        super(entityTypeParam, fullUri);
    }
}
