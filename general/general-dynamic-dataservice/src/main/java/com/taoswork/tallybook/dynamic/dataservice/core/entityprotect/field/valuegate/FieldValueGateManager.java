package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.valuegate;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.field.handler.FieldTypedHandlerManager;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FieldValueGateManager extends FieldTypedHandlerManager<IFieldValueGate> {

    public void normalize(Persistable entity, ClassMetadata classMetadata) throws ServiceException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        try {
            for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()) {
                String fieldName = fieldMetadataEntry.getKey();
                IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
                Field field = fieldMetadata.getField();
                Object fieldValue = field.get(entity);
                fieldValue = this.normalize(fieldMetadata, fieldValue);
                field.set(entity, fieldValue);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }
    }

    public Object normalize(IFieldMetadata fieldMetadata, Object fieldValue) {
        String fieldName = fieldMetadata.getName();
        Collection<IFieldValueGate> fieldValueGates = this.getHandlers(fieldMetadata);
        for (IFieldValueGate valueGate : fieldValueGates) {
            fieldValue = valueGate.deposit(fieldValue);
        }
        return fieldValue;
    }
}