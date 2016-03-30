package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import java.lang.reflect.Field;

public class PresentationAnnotationFieldHandler
        implements IFieldHandler {
    public PresentationAnnotationFieldHandler() {
    }

    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        MutableClassMeta mutableClassMeta = metaMediate.getMutableClassMetadata();
        PresentationField presentationField = mutableClassMeta.getPresentationFieldOverride(field.getName());
        if (presentationField == null){
            presentationField = field.getDeclaredAnnotation(PresentationField.class);
        }

        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();

        bfmo.setTabName(PresentationClass.Tab.DEFAULT_NAME);
        bfmo.setGroupName(PresentationClass.Group.DEFAULT_NAME);
        bfmo.setOrder(PresentationField.DEFAULT_ORDER_BIAS + bfmo.getOriginalOrder());
        bfmo.setVisibility(Visibility.DEFAULT);
        bfmo.setNameField(false);

        if (presentationField != null) {
            bfmo.setTabName(presentationField.tab());
            bfmo.setGroupName(presentationField.group());
            if (presentationField.order() != PresentationField.ORDER_NOT_DEFINED) {
                bfmo.setOrder(presentationField.order());
            }
            bfmo.setVisibility(presentationField.visibility());
            bfmo.setIgnored(presentationField.ignore());
        }
        return ProcessResult.HANDLED;
    }
}
