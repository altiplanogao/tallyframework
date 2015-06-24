package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse extends AEntityQueryResponse{
    private ClassEdo classEdo;
    private List<?> entities;

    public ClassEdo getClassEdo() {
        return classEdo;
    }

    public EntityQueryResponse setClassEdo(ClassEdo classEdo) {
        this.classEdo = classEdo;
        return this;
    }

    public List<?> getEntities() {
        return entities;
    }

    public EntityQueryResponse setEntities(List<?> entities) {
        this.entities = entities;
        return this;
    }

}