package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.Errors;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityResponse extends ResourceSupport {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityResponse.class);
    Errors errors;
    private String resourceName;
    private Class<?> entityCeilingType;
    private Class<?> entityType;
    private String baseUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EntityInfoResult info;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<String> actions;

    public String getResourceName() {
        return resourceName;
    }

    public EntityResponse setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public Class<?> getEntityCeilingType() {
        return entityCeilingType;
    }

    public EntityResponse setEntityCeilingType(Class<?> entityCeilingType) {
        this.entityCeilingType = entityCeilingType;
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

    public Collection<String> getActions() {
        return actions;
    }

    public void setActions(Collection<String> actions) {
        this.actions = actions;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public boolean hasError() {
        LOGGER.warn("class {} should handle error.", this.getClass().getName() ); //TODO
        return false;
    }
}
