package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValueGateService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.FieldValueGateManager;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.gates.EmailValueGate;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.gates.HtmlValueGate;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate.EntityValueGateManager;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import javax.annotation.Resource;

public class EntityValueGateServiceImpl implements EntityValueGateService {
    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    private FieldValueGateManager fieldValueGateManager = new FieldValueGateManager();
    private EntityValueGateManager entityValueGateManager = new EntityValueGateManager();

    public EntityValueGateServiceImpl() {
        fieldValueGateManager.addHandler(new EmailValueGate());
        fieldValueGateManager.addHandler(new HtmlValueGate());
    }

    @Override
    public <T extends Persistable> void deposit(T entity, T oldEntity) throws ServiceException {
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        fieldValueGateManager.normalize(entity, classMetadata);
        entityValueGateManager.deposit(classMetadata, entity, oldEntity);
    }

    @Override
    public <T extends Persistable> void withdraw(T entity) {
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        entityValueGateManager.withdraw(classMetadata, entity);
    }
}
