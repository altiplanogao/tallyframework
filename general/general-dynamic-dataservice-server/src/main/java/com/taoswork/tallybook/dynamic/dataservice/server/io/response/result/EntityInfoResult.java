package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityInfoResult {
    private String resourceName;
    private Class<?> entityType;
    private String baseUrl;

    private Map<String, IEntityInfo> details;

    public String getResourceName() {
        return resourceName;
    }

    public EntityInfoResult setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public EntityInfoResult setEntityType(Class<?> entityType) {
        this.entityType = entityType;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public EntityInfoResult setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, IEntityInfo> getDetails(){
        if (null == details){
            return null;
        }
        return Collections.unmodifiableMap(details);
    }

    public EntityInfoResult setDetails(Map<String, IEntityInfo> details) {
        this.details = details;
        return this;
    }


    public EntityInfoResult addDetail(EntityInfoType infoType, IEntityInfo entityDetail){
        if(this.details == null){
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.put(infoType.getName(), entityDetail);
        return this;
    }

    public EntityInfoResult addDetails(Map<String, IEntityInfo> entityDetailMap){
        if(this.details == null){
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.putAll(entityDetailMap);
        return this;
    }

    public <T extends IEntityInfo> T getDetail(String infoType){
        return (T) details.getOrDefault(infoType, null);
    }

    public <T extends IEntityInfo> T getDetail(EntityInfoType infoType){
        return (T) details.getOrDefault(infoType.getName(), null);
    }

}
