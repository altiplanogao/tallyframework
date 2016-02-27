package com.taoswork.tallybook.dataservice.server.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityInfoResult {
    private Class<?> type;
    private Class<?> ceilingType;
    private String idField;
    private String nameField;

    private String beanUri;

    private Map<String, IEntityInfo> details;

    public Class<?> getCeilingType() {
        return ceilingType;
    }

    public EntityInfoResult setCeilingType(Class<?> ceilingType) {
        this.ceilingType = ceilingType;
        return this;
    }

    public Class<?> getType() {
        return type;
    }

    public EntityInfoResult setType(Class<?> type) {
        this.type = type;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBeanUri() {
        return beanUri;
    }

    public void setBeanUri(String beanUri) {
        this.beanUri = beanUri;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIdField() {
        return idField;
    }

    public void setIdFieldIfEmpty(String idField) {
        if (StringUtils.isEmpty(this.idField)) {
            setIdField(idField);
        }
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNameField() {
        return nameField;
    }

    public void setNameFieldIfEmpty(String nameField) {
        if (StringUtils.isEmpty(this.nameField)) {
            setNameField(nameField);
        }
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, IEntityInfo> getDetails() {
        if (null == details) {
            return null;
        }
        return Collections.unmodifiableMap(details);
    }

    public EntityInfoResult setDetails(Map<String, IEntityInfo> details) {
        this.details = details;
        return this;
    }


    public EntityInfoResult addDetail(String typeName, IEntityInfo entityDetail) {
        if (this.details == null) {
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.put(typeName, entityDetail);
        return this;
    }

    public EntityInfoResult addDetails(Map<String, IEntityInfo> entityDetailMap) {
        if (this.details == null) {
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.putAll(entityDetailMap);
        return this;
    }

    public <T extends IEntityInfo> T getDetail(String infoType) {
        return (T) details.get(infoType);
    }

    public <T extends IEntityInfo> T getDetail(EntityInfoType infoType) {
        return (T) details.get(infoType.getType());
    }

}
