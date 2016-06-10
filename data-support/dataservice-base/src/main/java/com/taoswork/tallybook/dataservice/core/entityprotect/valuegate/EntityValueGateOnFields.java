package com.taoswork.tallybook.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.FieldGatePool;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.handler.TypedFieldHandlerManager;
import com.taoswork.tallybook.dataservice.core.entityprotect.field.valuegate.TypedFieldGate;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EntityValueGateOnFields
        extends TypedFieldHandlerManager<TypedFieldGate>
        implements EntityValueGate {

    private final FieldGatePool fieldGatePool = new FieldGatePool();

    @Override
    public void store(IClassMeta classMeta, Persistable entity, Persistable oldEntity) throws ServiceException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        try {
            for (Map.Entry<String, IFieldMeta> fieldMetaEntry : classMeta.getReadonlyFieldMetaMap().entrySet()) {
                String fieldName = fieldMetaEntry.getKey();
                IFieldMeta fieldMeta = fieldMetaEntry.getValue();
                Field field = fieldMeta.getField();
                Object fieldValue = field.get(entity);
                Object oldFieldValue = null;
                if (oldEntity != null) {
                    oldFieldValue = field.get(oldEntity);
                }
                fieldValue = this.storeField(fieldMeta, fieldValue, oldFieldValue);
                field.set(entity, fieldValue);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void fetch(IClassMeta classMeta, Persistable entity) {

    }

    public Object storeField(IFieldMeta fieldMeta, Object fieldValue, Object oldFieldValue) {
        String fieldName = fieldMeta.getName();
        Class<? extends IFieldGate> fieldValueGateCls = fieldMeta.getFieldValueGateOverride();
        boolean skipDefaultFieldValueGate = fieldMeta.getSkipDefaultFieldValueGate();

        IFieldGate fieldValueGate = fieldGatePool.getValueGate(fieldValueGateCls);
        if (fieldValueGate != null) {
            fieldValue = fieldValueGate.store(fieldValue, oldFieldValue);
        }
        if (!skipDefaultFieldValueGate) {
            Collection<TypedFieldGate> typedFieldValueGates = this.getHandlers(fieldMeta);
            for (TypedFieldGate valueGate : typedFieldValueGates) {
                fieldValue = valueGate.store(fieldValue, oldFieldValue);
            }
        }
        return fieldValue;
    }
}
