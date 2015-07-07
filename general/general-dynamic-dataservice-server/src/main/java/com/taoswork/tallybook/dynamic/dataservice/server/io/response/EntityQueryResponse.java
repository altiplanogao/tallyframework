package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse extends AEntityQueryResponse{
    private List<?> details;

    public List<?> getDetails() {
        return details;
    }

    public EntityQueryResponse setDetails(List<?> details) {
        this.details = details;
        return this;
    }

}
