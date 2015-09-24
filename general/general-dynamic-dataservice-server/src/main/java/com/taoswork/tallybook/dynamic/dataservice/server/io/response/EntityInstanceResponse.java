package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInstanceResult;
import org.springframework.validation.Errors;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public abstract class EntityInstanceResponse extends EntityResponse {
    EntityInstanceResult entity;
    Errors errors;

    public EntityInstanceResult getEntity() {
        return entity;
    }

    public EntityInstanceResponse setEntity(EntityInstanceResult entity) {
        this.entity = entity;
        return this;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
