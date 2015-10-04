package com.taoswork.tallybook.dynamic.dataservice.core.validate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationException;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationService;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.entity.EntityValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.FieldValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.EmailFieldValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.FieldLengthValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.FieldRequiredValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.validator.PhoneFieldValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.FieldValidationErrors;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationServiceImpl implements EntityValidationService {
    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    private FieldValidatorManager fieldValidatorManager = new FieldValidatorManager();

    private EntityValidatorManager entityValidatorManager = new EntityValidatorManager();

    public EntityValidationServiceImpl(){
        fieldValidatorManager
            .addValidator(new FieldRequiredValidator())
            .addValidator(new FieldLengthValidator())
            .addValidator(new EmailFieldValidator())
            .addValidator(new PhoneFieldValidator());
    }

    @Override
    public void validate(EntityResult entityResult) throws ServiceException {
        Persistable entity = entityResult.getEntity();
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        try {
            EntityValidationErrors entityErrors = new EntityValidationErrors();
            List<String> fieldFriendlyNames = new ArrayList<String>();
            for (Map.Entry<String, FieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()){
                String fieldName = fieldMetadataEntry.getKey();
                FieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
                Field field = fieldMetadata.getField();
                Object fieldValue = field.get(entity);
                FieldValidationErrors fieldError = fieldValidatorManager.validate(fieldMetadata, fieldValue);
                if(!fieldError.isValid()){
                    fieldFriendlyNames.add(fieldMetadata.getFriendlyName());
                    entityErrors.addFieldErrors(fieldError);
                }
            }
            entityErrors.appendErrorFieldsNames(fieldFriendlyNames);

            entityValidatorManager.validate(entity, classMetadata, entityErrors);

            if(!entityErrors.isValid()){
                throw new EntityValidationException(entityResult, entityErrors);
            }

        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }

    }
}
