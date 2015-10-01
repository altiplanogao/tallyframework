package com.taoswork.tallybook.dynamic.dataservice.core.validate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationException;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationResult;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationService;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.FieldValidationResult;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.ValueValidationResult;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.FieldValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.IFieldValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.EmailFieldValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.FieldLengthValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.FieldRequiredValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.PhoneFieldValidator;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationServiceImpl implements EntityValidationService {
    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    private FieldValidatorManager fieldValidatorManager = new FieldValidatorManager();

    public EntityValidationServiceImpl(){
        fieldValidatorManager
            .addValidator(new FieldRequiredValidator())
            .addValidator(new FieldLengthValidator())
            .addValidator(new EmailFieldValidator())
            .addValidator(new PhoneFieldValidator());
    }

    @Override
    public void validate(EntityResult entityResult) throws ServiceException {
        Object entity = entityResult.getEntity();
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        try {
            EntityValidationResult entityValidationResult = new EntityValidationResult();
            for (Map.Entry<String, FieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()){
                String fieldName = fieldMetadataEntry.getKey();
                FieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
                Field field = fieldMetadata.getField();
                Object fieldValue = field.get(entity);
                FieldValidationResult fieldValidationResult = fieldValidatorManager.validate(fieldMetadata, fieldValue);
                entityValidationResult.appendFieldValidationResult(fieldValidationResult);
            }

            if(!entityValidationResult.isValid()){
                throw new EntityValidationException(entityResult, entityValidationResult);
            }

        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }

    }
}
