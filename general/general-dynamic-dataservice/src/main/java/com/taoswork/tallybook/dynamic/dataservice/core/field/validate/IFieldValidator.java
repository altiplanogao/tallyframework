package com.taoswork.tallybook.dynamic.dataservice.core.field.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.field.handler.IFieldTypedHandler;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface IFieldValidator extends IFieldTypedHandler {
    ValidationError validate(FieldMetadata fieldMetadata, Object fieldValue);
}
