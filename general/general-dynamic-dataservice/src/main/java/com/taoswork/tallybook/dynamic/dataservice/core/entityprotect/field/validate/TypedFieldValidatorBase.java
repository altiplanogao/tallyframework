package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.TypedFieldHandlerBase;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public abstract class TypedFieldValidatorBase<T>
    extends TypedFieldHandlerBase<T>
    implements TypedFieldValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(TypedFieldValidatorBase.class);

    protected boolean nullValueAsValid(IFieldMetadata fieldMetadata) {
        if(fieldMetadata.isRequired())
            return false;
        return true;
    }

    @Override
    public final ValidationError validate(IFieldMetadata fieldMetadata, Object fieldValue) {
        if (canHandle(fieldMetadata)) {
            if (null == fieldValue) {
                if (nullValueAsValid(fieldMetadata)) {
                    return null;
                } else {
                    return new ValidationError("validation.error.field.required");
                }
            }
            ValidationError result = this.doValidate(fieldMetadata, (T) fieldValue);
            return result;
        }
        return null;
    }

    protected abstract ValidationError doValidate(IFieldMetadata fieldMetadata, T fieldValue);
}
