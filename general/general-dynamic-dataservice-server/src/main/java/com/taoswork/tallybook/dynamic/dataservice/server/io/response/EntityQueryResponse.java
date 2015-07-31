package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse extends AEntityQueryResponse{
    private List<?> details;
    private String baseUrl;

    public List<?> getDetails() {
        return details;
    }

    public EntityQueryResponse setDetails(List<?> details) {
        this.details = details;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public EntityQueryResponse setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
