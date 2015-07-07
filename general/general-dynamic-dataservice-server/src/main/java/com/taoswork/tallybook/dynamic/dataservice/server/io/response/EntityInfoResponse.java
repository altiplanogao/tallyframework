package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
public class EntityInfoResponse {
    //Entity Type
    public final String type;
    public final String simpleType;
    private Map<String, IEntityInfo> details;

    public EntityInfoResponse(Class<?> type, String simpleType) {
        this.type = type.getName();
        this.simpleType = simpleType;
    }
    public EntityInfoResponse(String type, String simpleType) {
        this.type = type;
        this.simpleType = simpleType;
    }

    public EntityInfoResponse addDetail(EntityInfoType infoType, IEntityInfo entityInfo){
        if(this.details == null){
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.put(infoType.getName(), entityInfo);
        return this;
    }

    public EntityInfoResponse addDetails(Map<String, IEntityInfo> entityInfoMap){
        if(this.details == null){
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.putAll(entityInfoMap);
        return this;
    }

    public <T extends IEntityInfo> T getDetail(String infoType){
        return (T) details.getOrDefault(infoType, null);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, IEntityInfo> getDetails(){
        if (null == details){
            return null;
        }
        return Collections.unmodifiableMap(details);
    }
}
