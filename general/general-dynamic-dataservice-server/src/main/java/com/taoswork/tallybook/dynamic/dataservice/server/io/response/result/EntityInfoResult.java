package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityInfoResult {
    private String resourceName;
    private Class<?> entityCeilingType;
    private Class<?> entityType;
    private String entityUri;
    private String beanUri;

    private Map<String, IEntityInfo> details;

    public String getResourceName() {
        return resourceName;
    }

    public EntityInfoResult setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public Class<?> getEntityCeilingType() {
        return entityCeilingType;
    }

    public EntityInfoResult setEntityCeilingType(Class<?> entityCeilingType) {
        this.entityCeilingType = entityCeilingType;
        return this;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public EntityInfoResult setEntityType(Class<?> entityType) {
        this.entityType = entityType;
        return this;
    }

    public String getEntityUri() {
        return entityUri;
    }

    public EntityInfoResult setEntityUri(String entityUri) {
        this.entityUri = entityUri;
        return this;
    }

    public String getBeanUri() {
        return beanUri;
    }

    public void setBeanUri(String beanUri) {
        this.beanUri = beanUri;
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


    public EntityInfoResult addDetail(String typeName, IEntityInfo entityDetail){
        if(this.details == null){
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.put(typeName, entityDetail);
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
        return (T) details.get(infoType);
    }

    public <T extends IEntityInfo> T getDetail(EntityInfoType infoType){
        return (T) details.get(infoType.getType());
    }

}
