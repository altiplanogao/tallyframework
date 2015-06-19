package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Entity;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse {
    private ClassEdo classEdo;
    private List<Entity> entities;
    private Long startIndex;
    private Long totalCount;

    public ClassEdo getClassEdo() {
        return classEdo;
    }

    public EntityQueryResponse setClassEdo(ClassEdo classEdo) {
        this.classEdo = classEdo;
        return this;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public EntityQueryResponse setEntities(List<Entity> entities) {
        this.entities = entities;
        return this;
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public EntityQueryResponse setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public EntityQueryResponse setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
