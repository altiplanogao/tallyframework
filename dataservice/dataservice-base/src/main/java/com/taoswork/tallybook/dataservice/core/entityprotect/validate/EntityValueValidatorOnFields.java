package com.taoswork.tallybook.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.FieldValidationErrors;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.handler.TypedFieldHandlerManager;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.validate.TypedFieldValidator;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public class EntityValueValidatorOnFields extends TypedFieldHandlerManager<TypedFieldValidator>
        implements EntityValueValidator {

    @Override
    public void validate(Persistable entity, IClassMeta classMeta, EntityValidationErrors entityErrors) throws IllegalAccessException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        for (Map.Entry<String, IFieldMeta> fieldMetaEntry : classMeta.getReadonlyFieldMetaMap().entrySet()) {
            String fieldName = fieldMetaEntry.getKey();
            IFieldMeta fieldMeta = fieldMetaEntry.getValue();
            Field field = fieldMeta.getField();
            Object fieldValue = field.get(entity);
            FieldValidationErrors fieldError = this.validate(fieldMeta, fieldValue);
            if (!fieldError.isValid()) {
                fieldFriendlyNames.add(fieldMeta.getFriendlyName());
                entityErrors.addFieldErrors(fieldError);
            }
        }
        entityErrors.appendErrorFieldsNames(fieldFriendlyNames);
    }

    private FieldValidationErrors validate(IFieldMeta fieldMeta, Object fieldValue) {
        String fieldName = fieldMeta.getName();
        FieldValidationErrors fieldValidationErrors = new FieldValidationErrors(fieldName);
        Collection<TypedFieldValidator> validators = this.getHandlers(fieldMeta);
        for (TypedFieldValidator validator : validators) {
            ValidationError validateError = validator.validate(fieldMeta, fieldValue);
            fieldValidationErrors.appendError(validateError);
        }

        return fieldValidationErrors;
    }

}
