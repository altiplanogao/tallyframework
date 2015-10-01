package com.taoswork.tallybook.dynamic.dataservice.core.validate.field;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.ValueValidationResult;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public abstract class FieldValidatorBase<T> implements IFieldValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(FieldValidatorBase.class);

    protected boolean canHandle(FieldMetadata fieldMetadata) {
        Class fieldClass = fieldMetadata.getFieldClass();
        FieldType fieldType = fieldMetadata.getFieldType();

        Class supportedClass = this.supportedFieldClass();
        FieldType supportedType = this.supportedFieldType();

        if (supportedType != null) {
            if (!supportedType.equals(fieldType)) {
                return false;
            }
        }

        if (supportedClass != null) {
            if (!supportedClass.equals(fieldClass)) {
                return false;
            }
        }

        return true;
    }

    protected boolean nullValueAsValid(){
        return true;
    }

    @Override
    public final ValueValidationResult validate(FieldMetadata fieldMetadata, Object fieldValue) {
        if(canHandle(fieldMetadata)){
            if(null == fieldValue){
                if(nullValueAsValid())
                    return null;
            }
            ValueValidationResult result = this.doValidate(fieldMetadata, (T)fieldValue);
            return result;
        }
        return null;
    }

    protected abstract ValueValidationResult doValidate(FieldMetadata fieldMetadata, T fieldValue);
}
