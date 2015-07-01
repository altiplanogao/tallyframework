package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import java.util.List;

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
