package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.IFieldTypedHandler;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface IFieldValidator extends IFieldTypedHandler {
    ValidationError validate(IFieldMetadata fieldMetadata, Object fieldValue);
}
