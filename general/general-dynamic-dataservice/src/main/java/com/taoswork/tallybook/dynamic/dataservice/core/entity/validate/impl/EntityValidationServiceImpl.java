package com.taoswork.tallybook.dynamic.dataservice.core.entity.validate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.entity.validate.EntityValidationException;
import com.taoswork.tallybook.dynamic.dataservice.core.entity.EntityValidationService;
import com.taoswork.tallybook.dynamic.dataservice.core.entity.validate.EntityValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.field.validate.FieldValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.field.validate.validator.EmailFieldValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.field.validate.validator.FieldLengthValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.field.validate.validator.FieldRequiredValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.field.validate.validator.PhoneFieldValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

import javax.annotation.Resource;

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
            .addHandler(new FieldRequiredValidator())
            .addHandler(new FieldLengthValidator())
            .addHandler(new EmailFieldValidator())
            .addHandler(new PhoneFieldValidator());
    }

    @Override
    public void validate(EntityResult entityResult) throws ServiceException {
        Persistable entity = entityResult.getEntity();
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        try {
            EntityValidationErrors entityErrors = new EntityValidationErrors();

            fieldValidatorManager.validate(entity, classMetadata, entityErrors);
            entityValidatorManager.validate(entity, classMetadata, entityErrors);

            if(!entityErrors.isValid()){
                throw new EntityValidationException(entityResult, entityErrors);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }

    }

}
