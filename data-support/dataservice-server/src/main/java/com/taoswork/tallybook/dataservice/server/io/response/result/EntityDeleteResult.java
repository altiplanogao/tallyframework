package com.taoswork.tallybook.dataservice.server.io.response.result;

/**
 * Created by Gao Yuan on 2015/9/30.
 */
public class EntityDeleteResult {
    private Long id;
    private String entityType;
    private String entityCeilingType;
    private boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityCeilingType() {
        return entityCeilingType;
    }

    public void setEntityCeilingType(String entityCeilingType) {
        this.entityCeilingType = entityCeilingType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
