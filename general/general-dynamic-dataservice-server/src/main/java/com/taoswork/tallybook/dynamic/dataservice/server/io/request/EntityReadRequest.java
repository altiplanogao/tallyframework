package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

public class EntityReadRequest extends EntityRequest {

    private long id;

    public long getId() {
        return id;
    }

    public EntityReadRequest setId(String id) {
        this.id = Long.parseLong(id);
        return this;
    }

    public EntityReadRequest setId(long id) {
        this.id = id;
        return this;
    }
}
