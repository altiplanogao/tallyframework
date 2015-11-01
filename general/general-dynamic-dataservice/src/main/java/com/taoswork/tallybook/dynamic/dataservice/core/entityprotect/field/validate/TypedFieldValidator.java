package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.ITypedFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface TypedFieldValidator extends ITypedFieldHandler {
    ValidationError validate(IFieldMetadata fieldMetadata, Object fieldValue);
}
