package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.IEntityInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse extends AEntityQueryResponse{
    private List<?> entities;

    public List<?> getEntities() {
        return entities;
    }

    public EntityQueryResponse setEntities(List<?> entities) {
        this.entities = entities;
        return this;
    }

}
