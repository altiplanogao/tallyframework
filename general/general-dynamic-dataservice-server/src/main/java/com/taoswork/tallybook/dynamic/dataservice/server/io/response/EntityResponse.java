package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInfoResult;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityResponse extends ResourceSupport {
    private String resourceName;
    private Class<?> entityType;
    private String baseUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EntityInfoResult info;

    public String getResourceName() {
        return resourceName;
    }

    public EntityResponse setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public EntityResponse setEntityType(Class<?> entityType) {
        this.entityType = entityType;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public EntityResponse setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public EntityInfoResult getInfo() {
        return info;
    }

    public EntityResponse setInfo(EntityInfoResult info) {
        this.info = info;
        return this;
    }
}
