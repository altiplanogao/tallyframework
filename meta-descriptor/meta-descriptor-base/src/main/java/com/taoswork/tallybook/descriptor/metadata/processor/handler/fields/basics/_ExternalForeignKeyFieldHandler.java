package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationExternalForeignKey;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;

import java.lang.reflect.Field;

class _ExternalForeignKeyFieldHandler extends BaseFieldHandler {

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        PersistField persistField = field.getDeclaredAnnotation(PersistField.class);
        if (persistField != null && FieldType.EXTERNAL_FOREIGN_KEY.equals(persistField.fieldType())) {
            return true;
        }
        return false;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        try {
            PresentationExternalForeignKey presentationExternalForeignKey = field.getDeclaredAnnotation(PresentationExternalForeignKey.class);
            if (presentationExternalForeignKey != null) {
                Class hostClass = field.getDeclaringClass();

                String dataField = presentationExternalForeignKey.dataField();
                Field realDataField = hostClass.getDeclaredField(dataField);
                Class declaredType = realDataField.getType();
                Class targetType = presentationExternalForeignKey.targetType();

                if (void.class.equals(targetType)) {
                    targetType = declaredType;
                }
                ExternalForeignEntityFieldMeta.Seed seed = new ExternalForeignEntityFieldMeta.Seed(dataField,
                        declaredType, targetType, presentationExternalForeignKey.idField(), presentationExternalForeignKey.displayField());
                metaMediate.setMetaSeed(seed);
                return ProcessResult.HANDLED;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return ProcessResult.INAPPLICABLE;
        }

        return ProcessResult.INAPPLICABLE;
    }
	

}
