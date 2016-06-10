package com.taoswork.tallybook.dataservice.core.entityprotect.field.validate;

import com.taoswork.tallybook.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.handler.ITypedFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface TypedFieldValidator extends ITypedFieldHandler {
    ValidationError validate(IFieldMeta fieldMeta, Object fieldValue);
}
