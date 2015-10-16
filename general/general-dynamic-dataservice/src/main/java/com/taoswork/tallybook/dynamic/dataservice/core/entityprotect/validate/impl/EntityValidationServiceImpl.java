package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.PersistableResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValidationService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.FieldValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator.EmailFieldValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator.FieldLengthValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator.FieldRequiredValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator.PhoneFieldValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate.EntityValidationException;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate.EntityValidatorManager;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
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

    public EntityValidationServiceImpl() {
        fieldValidatorManager
            .addHandler(new FieldRequiredValidator())
            .addHandler(new FieldLengthValidator())
            .addHandler(new EmailFieldValidator())
            .addHandler(new PhoneFieldValidator());
    }

    @Override
    public void validate(PersistableResult persistableResult) throws ServiceException {
        Persistable entity = persistableResult.getEntity();
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        try {
            EntityValidationErrors entityErrors = new EntityValidationErrors();

            fieldValidatorManager.validate(entity, classMetadata, entityErrors);
            entityValidatorManager.validate(entity, classMetadata, entityErrors);

            if (!entityErrors.isValid()) {
                throw new EntityValidationException(persistableResult, entityErrors);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }

    }

}
