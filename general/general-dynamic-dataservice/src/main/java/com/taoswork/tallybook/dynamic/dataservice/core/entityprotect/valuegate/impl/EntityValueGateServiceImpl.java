package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.EntityValueGateService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate.EntityValueGate;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate.EntityValueGateOnFields;
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

    private final EntityValueGate entityValueGateOnFields;
    private final EntityValueGate entityValueGateManager;

    public EntityValueGateServiceImpl() {
        EntityValueGateOnFields valueGateOnFields = new EntityValueGateOnFields();
        valueGateOnFields
            .addHandler(new EmailValueGate())
            .addHandler(new HtmlValueGate());
        entityValueGateOnFields = valueGateOnFields;
        entityValueGateManager = new EntityValueGateManager();
    }

    @Override
    public <T extends Persistable> void store(T entity, T oldEntity) throws ServiceException {
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        entityValueGateOnFields.store(classMetadata, entity, oldEntity);
        entityValueGateManager.store(classMetadata, entity, oldEntity);
    }

    @Override
    public <T extends Persistable> void fetch(T entity) {
        Class entityType = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(entityType, false);

        entityValueGateManager.fetch(classMetadata, entity);
    }
}
