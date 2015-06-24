package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Entity;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryListGridResponse  extends AEntityQueryResponse{
    private ClassEdo classEdo;
    private List<Entity> entities;

    public ClassEdo getClassEdo() {
        return classEdo;
    }

    public EntityQueryListGridResponse setClassEdo(ClassEdo classEdo) {
        this.classEdo = classEdo;
        return this;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public EntityQueryListGridResponse setEntities(List<Entity> entities) {
        this.entities = entities;
        return this;
    }

}