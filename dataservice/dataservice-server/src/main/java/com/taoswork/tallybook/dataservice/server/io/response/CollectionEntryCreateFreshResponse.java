package com.taoswork.tallybook.dataservice.server.io.response;

import com.taoswork.tallybook.datadomain.base.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/11/26.
 */
public class CollectionEntryCreateFreshResponse extends EntityCreateFreshResponse {
    public CollectionEntryCreateFreshResponse(String createUri) {
        super(createUri);
    }

    @Override
    public String getAction() {
        return EntityAction.CREATE.getType();
    }
}
