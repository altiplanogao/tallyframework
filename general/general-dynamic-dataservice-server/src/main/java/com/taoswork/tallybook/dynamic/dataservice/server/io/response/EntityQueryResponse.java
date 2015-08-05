package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityQueryResult;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse extends EntityResponse{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    EntityQueryResult entities;

    public EntityQueryResult getEntities() {
        return entities;
    }

    public EntityQueryResponse setEntities(EntityQueryResult entities) {
        this.entities = entities;
        return this;
    }
}
