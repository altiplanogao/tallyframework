package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityReadResponse extends EntityInstanceResponse {
    @Override
    public String getAction() {
        return EntityActionNames.READ;
    }

    @Override
    public boolean success() {
        return super.success();
    }

    public boolean gotRecord(){
        return (getEntity() != null && getEntity().getData() != null);
    }
}
