package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityReadResult {
    Object data;

    public Object getData() {
        return data;
    }

    public EntityReadResult setData(Object data) {
        this.data = data;
        return this;
    }
}
