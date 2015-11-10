package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.TypedFieldHandlerManager;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate.TypedFieldValueGate;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IFieldValueGate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EntityValueGateOnFields
    extends TypedFieldHandlerManager<TypedFieldValueGate>
    implements EntityValueGate {

    private final FieldValueGateManager fieldValueGateManager = new FieldValueGateManager();

    @Override
    public void store(IClassMetadata classMetadata, Persistable entity, Persistable oldEntity) throws ServiceException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        try {
            for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()) {
                String fieldName = fieldMetadataEntry.getKey();
                IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
                Field field = fieldMetadata.getField();
                Object fieldValue = field.get(entity);
                Object oldFieldValue = null;
                if (oldEntity != null) {
                    oldFieldValue = field.get(oldEntity);
                }
                fieldValue = this.storeField(fieldMetadata, fieldValue, oldFieldValue);
                field.set(entity, fieldValue);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void fetch(IClassMetadata classMetadata, Persistable entity) {

    }

    public Object storeField(IFieldMetadata fieldMetadata, Object fieldValue, Object oldFieldValue) {
        String fieldName = fieldMetadata.getName();
        Class<? extends IFieldValueGate> fieldValueGateCls = fieldMetadata.getFieldValueGateOverride();
        boolean skipDefaultFieldValueGate = fieldMetadata.getSkipDefaultFieldValueGate();

        IFieldValueGate fieldValueGate = fieldValueGateManager.getValueGate(fieldValueGateCls);
        if(fieldValueGate != null){
            fieldValue = fieldValueGate.store(fieldValue, oldFieldValue);
        }
        if(!skipDefaultFieldValueGate) {
            Collection<TypedFieldValueGate> typedFieldValueGates = this.getHandlers(fieldMetadata);
            for (TypedFieldValueGate valueGate : typedFieldValueGates) {
                fieldValue = valueGate.store(fieldValue, oldFieldValue);
            }
        }
        return fieldValue;
    }
}
