package com.taoswork.tallybook.dynamic.dataservice.server.dto.response;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Entity;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse {
    ClassEdo classEdo;
    List<Entity> entities;

    public ClassEdo getClassEdo() {
        return classEdo;
    }

    public void setClassEdo(ClassEdo classEdo) {
        this.classEdo = classEdo;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
