package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Entity;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryListGridResponse  extends AEntityQueryResponse{
    private List<Entity> entities;
    private String baseUrl;

    public List<Entity> getEntities() {
        return entities;
    }

    public EntityQueryListGridResponse setEntities(List<Entity> entities) {
        this.entities = entities;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public EntityQueryListGridResponse setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
