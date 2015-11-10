package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.TypedFieldHandlerManager;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.TypedFieldValidator;
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
public class EntityValidatorOnFields extends TypedFieldHandlerManager<TypedFieldValidator>
    implements EntityValidator {

    @Override
    public void validate(Persistable entity, IClassMetadata classMetadata, EntityValidationErrors entityErrors) throws IllegalAccessException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()) {
            String fieldName = fieldMetadataEntry.getKey();
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            Field field = fieldMetadata.getField();
            Object fieldValue = field.get(entity);
            FieldValidationErrors fieldError = this.validate(fieldMetadata, fieldValue);
            if (!fieldError.isValid()) {
                fieldFriendlyNames.add(fieldMetadata.getFriendlyName());
                entityErrors.addFieldErrors(fieldError);
            }
        }
        entityErrors.appendErrorFieldsNames(fieldFriendlyNames);
    }

    private FieldValidationErrors validate(IFieldMetadata fieldMetadata, Object fieldValue) {
        String fieldName = fieldMetadata.getName();
        FieldValidationErrors fieldValidationErrors = new FieldValidationErrors(fieldName);
        Collection<TypedFieldValidator> validators = this.getHandlers(fieldMetadata);
        for (TypedFieldValidator validator : validators) {
            ValidationError validateError = validator.validate(fieldMetadata, fieldValue);
            fieldValidationErrors.appendError(validateError);
        }

        return fieldValidationErrors;
    }

}
