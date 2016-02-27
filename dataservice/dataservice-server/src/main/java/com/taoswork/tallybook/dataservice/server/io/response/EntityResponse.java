package com.taoswork.tallybook.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityErrors;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public abstract class EntityResponse extends ResourceSupport {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityResponse.class);
    private Class<? extends Persistable> entityCeilingType;
    private Class<? extends Persistable> entityType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EntityInfoResult infos;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<String> actions;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EntityErrors errors;

    public abstract String getAction();

    public Class<? extends Persistable> getEntityCeilingType() {
        return entityCeilingType;
    }

    public EntityResponse setEntityCeilingType(Class<? extends Persistable> entityCeilingType) {
        this.entityCeilingType = entityCeilingType;
        return this;
    }

    public Class<? extends Persistable> getEntityType() {
        return entityType;
    }

    public EntityResponse setEntityType(Class<? extends Persistable> entityType) {
        this.entityType = entityType;
        return this;
    }

    public EntityInfoResult getInfos() {
        return infos;
    }

    public EntityResponse setInfos(EntityInfoResult infos) {
        this.infos = infos;
        return this;
    }

    public Collection<String> getActions() {
        return actions;
    }

    public void setActions(Collection<String> actions) {
        this.actions = actions;
    }

    public EntityErrors getErrors() {
        if (errors == null) {
            errors = new EntityErrors();
        }
        return errors;
    }

    public void setErrors(EntityErrors errors) {
        this.errors = errors;
    }

    public boolean success() {
        if (errors == null)
            return true;
        if (errors.containsError())
            return false;
        return true;
    }
}
