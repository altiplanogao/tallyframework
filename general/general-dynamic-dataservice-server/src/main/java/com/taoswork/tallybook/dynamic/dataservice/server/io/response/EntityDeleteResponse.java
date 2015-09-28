package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityDeleteResponse extends EntityResponse {
    boolean success = false;

    public boolean isSuccess() {
        return success;
    }

    public EntityDeleteResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
