package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import java.lang.reflect.Field;

public class PersistAnnotationFieldHandler
        implements IFieldHandler {

    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();
        bfmo.setFieldType(FieldType.UNKNOWN);
        MutableClassMeta mutableClassMeta = metaMediate.getMutableClassMetadata();
        PersistField persistField = mutableClassMeta.getPersistFieldOverride(field.getName());
        if (persistField == null){
            persistField = field.getDeclaredAnnotation(PersistField.class);
        }

        Class<? extends IFieldGate> fvg = null;
        if (persistField != null) {
            bfmo.setFieldType(persistField.fieldType());
            bfmo.setNameField(FieldType.NAME.equals(persistField.fieldType()));
            bfmo.setRequired(persistField.required());
            bfmo.setLength(persistField.length());

            bfmo.setEditable(persistField.editable());

            fvg = persistField.fieldValueGateOverride();
            if (IFieldGate.class.equals(fvg)) {
                fvg = null;
            }
            boolean skipDefaultFieldValueGate = persistField.skipDefaultFieldValueGate();
            bfmo.setFieldValueGate(fvg, skipDefaultFieldValueGate);

            return ProcessResult.HANDLED;
        } else {
            return ProcessResult.PASSING_THROUGH;
        }
    }
}
