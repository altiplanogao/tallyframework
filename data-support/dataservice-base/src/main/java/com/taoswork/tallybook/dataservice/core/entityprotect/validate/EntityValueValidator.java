package com.taoswork.tallybook.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface EntityValueValidator {
    void validate(Persistable entity, IClassMeta classMeta, EntityValidationErrors entityErrors) throws IllegalAccessException;
}
