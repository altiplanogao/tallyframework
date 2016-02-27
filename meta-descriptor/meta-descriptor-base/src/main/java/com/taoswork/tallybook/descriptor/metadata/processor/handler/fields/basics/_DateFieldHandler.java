package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateCellMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationDate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.DateFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _DateFieldHandler extends BaseFieldHandler {

    private boolean fits(Field field, FieldMetaMediate metaMediate) {
        boolean firstCheck = false;
        if (FieldType.DATE.equals(metaMediate.getBasicFieldMetaObject().getFieldType())) {
            firstCheck = true;
        }
        if (field.isAnnotationPresent(PresentationDate.class)) {
            firstCheck = true;
        }
        if (firstCheck) {
            Class fieldJavaType = field.getType();
            if (Date.class.equals(fieldJavaType) || Long.class.equals(fieldJavaType)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        return fits(field, metaMediate);
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        DateFieldMeta.Seed dateFieldSeed = null;
        PresentationDate presentationBoolean = field.getDeclaredAnnotation(PresentationDate.class);
        boolean useJavaDate = Date.class.equals(field.getType());
        DateMode model = DateMode.DateTime;
        DateCellMode cellModel = DateCellMode.DateAndTime;
        if (presentationBoolean != null) {
            model = presentationBoolean.mode();
            cellModel = presentationBoolean.cellMode();
        }
        dateFieldSeed = new DateFieldMeta.Seed(model, cellModel, useJavaDate);
        if (dateFieldSeed != null) {
            metaMediate.setMetaSeed(dateFieldSeed);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.FAILED;
    }

}
