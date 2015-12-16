package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.datadomain.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/11/26.
 */
public class CollectionEntryCreateFreshResponse extends EntityResponse {
    Object record;

    public Object getRecord() {
        return record;
    }

    public void setRecord(Object record) {
        this.record = record;
    }

    @Override
    public String getAction() {
        return EntityAction.CREATE.getType();
    }
}
