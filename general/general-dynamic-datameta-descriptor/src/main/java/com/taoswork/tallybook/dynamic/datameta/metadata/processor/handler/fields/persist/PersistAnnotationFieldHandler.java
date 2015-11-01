package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.persist;

import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IFieldValueGate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.lang.reflect.Field;

public class PersistAnnotationFieldHandler
    implements IFieldHandler {

    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        BasicFieldMetadataObject bfmo = fieldMetadata.getBasicFieldMetadataObject();
        bfmo.setFieldType(FieldType.UNKNOWN);
        PersistField persistField = field.getDeclaredAnnotation(PersistField.class);
        Class<? extends IFieldValueGate> fvg = null;
        if(persistField != null){
            bfmo.setFieldType(persistField.fieldType());
            bfmo.setNameField(FieldType.NAME.equals(persistField.fieldType()));
            if (persistField.required()) {
                bfmo.setRequired(true);
            }

            fvg = persistField.fieldValueGateOverride();
            if(IFieldValueGate.class.equals(fvg)){
                fvg = null;
            }
            boolean skipDefaultFieldValueGate = persistField.skipDefaultFieldValueGate();
            bfmo.setFieldValueGate(fvg, skipDefaultFieldValueGate);

            return ProcessResult.HANDLED;
        }else {
            return ProcessResult.PASSING_THROUGH;
        }
    }
}
