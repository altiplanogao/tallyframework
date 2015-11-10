package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface EntityValidator {
    void validate(Persistable entity, IClassMetadata classMetadata, EntityValidationErrors entityErrors) throws IllegalAccessException;
}
