package com.taoswork.tallybook.dynamic.dataservice.core.field.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.field.handler.FieldTypedHandlerManager;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.FieldValidationErrors;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public class FieldValidatorManager extends FieldTypedHandlerManager<IFieldValidator> {

    public void validate(Persistable entity, ClassMetadata classMetadata, EntityValidationErrors entityErrors) throws IllegalAccessException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        for (Map.Entry<String, FieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()){
            String fieldName = fieldMetadataEntry.getKey();
            FieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            Field field = fieldMetadata.getField();
            Object fieldValue = field.get(entity);
            FieldValidationErrors fieldError = this.validate(fieldMetadata, fieldValue);
            if(!fieldError.isValid()){
                fieldFriendlyNames.add(fieldMetadata.getFriendlyName());
                entityErrors.addFieldErrors(fieldError);
            }
        }
        entityErrors.appendErrorFieldsNames(fieldFriendlyNames);
    }

    public FieldValidationErrors validate(FieldMetadata fieldMetadata, Object fieldValue) {
        String fieldName = fieldMetadata.getName();
        FieldValidationErrors fieldValidationErrors = new FieldValidationErrors(fieldName);
        Collection<IFieldValidator> validators = this.getHandlers(fieldMetadata);
        for(IFieldValidator validator : validators){
            ValidationError validateError = validator.validate(fieldMetadata, fieldValue);
            fieldValidationErrors.appendError(validateError);
        }

        return fieldValidationErrors;
    }

}
