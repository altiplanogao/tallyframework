package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.PersistableResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValidationService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate.EntityValidator;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate.EntityValidatorOnFields;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.validate.validator.*;
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

    private final EntityValidator entityValidatorOnFields;
    private final EntityValidator entityValidatorManager;

    public EntityValidationServiceImpl() {
        EntityValidatorOnFields validatorOnFields = new EntityValidatorOnFields();
        validatorOnFields
            .addHandler(new FieldRequiredValidator())
            .addHandler(new FieldLengthValidator())
            .addHandler(new EmailFieldValidator())
            .addHandler(new PhoneFieldValidator())
            .addHandler(new ForeignKeyFieldValidator());

        entityValidatorOnFields = validatorOnFields;
        entityValidatorManager = new EntityValidatorManager();
    }

    @Override
    public void validate(PersistableResult persistableResult) throws ServiceException {
        Persistable entity = persistableResult.getEntity();
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        try {
            EntityValidationErrors entityErrors = new EntityValidationErrors();

            entityValidatorOnFields.validate(entity, classMetadata, entityErrors);
            entityValidatorManager.validate(entity, classMetadata, entityErrors);

            if (!entityErrors.isValid()) {
                throw new EntityValidationException(persistableResult, entityErrors);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }

    }

}
