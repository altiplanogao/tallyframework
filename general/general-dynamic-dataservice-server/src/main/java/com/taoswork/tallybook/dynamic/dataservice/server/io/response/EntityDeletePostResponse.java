package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityDeletePostResponse extends EntityResponse {
    boolean success = false;

    public boolean isSuccess() {
        return success;
    }

    public EntityDeletePostResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
