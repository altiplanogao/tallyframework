package com.taoswork.tallybook.dynamic.dataservice.server.dto.request;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryRequest {
    public EntityQueryRequest(){

    }

    Class<?> entityType;

    public EntityQueryRequest withEntityType(String entityType){
        try {
            this.entityType = Class.forName(entityType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public EntityQueryRequest withEntityType(Class<?> entityType){
        this.entityType = entityType;
        return this;
    }

    public Class<?> getEntityType() {
        return entityType;
    }
}
