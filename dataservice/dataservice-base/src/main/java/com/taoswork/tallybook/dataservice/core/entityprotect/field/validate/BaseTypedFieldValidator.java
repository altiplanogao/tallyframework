package com.taoswork.tallybook.dataservice.core.entityprotect.field.validate;

import com.taoswork.tallybook.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.handler.BaseTypedFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public abstract class BaseTypedFieldValidator<T>
        extends BaseTypedFieldHandler<T>
        implements TypedFieldValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseTypedFieldValidator.class);

    protected boolean nullValueAsValid(IFieldMeta fieldMeta) {
        if (fieldMeta.isRequired())
            return false;
        return true;
    }

    @Override
    public final ValidationError validate(IFieldMeta fieldMeta, Object fieldValue) {
        if (canHandle(fieldMeta)) {
            if (null == fieldValue) {
                if (nullValueAsValid(fieldMeta)) {
                    return null;
                } else {
                    return new ValidationError("validation.error.field.required");
                }
            }
            ValidationError result = this.doValidate(fieldMeta, (T) fieldValue);
            return result;
        }
        return null;
    }

    protected abstract ValidationError doValidate(IFieldMeta fieldMeta, T fieldValue);
}
