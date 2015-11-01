package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.DateFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.DateFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationDate;

import javax.persistence.Temporal;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _DateFieldHandler implements IFieldHandler {
    private boolean fits(Field field, FieldMetadataIntermediate fieldMetadata) {
        boolean firstCheck = false;
        if(FieldType.DATE.equals(fieldMetadata.getBasicFieldMetadataObject().getFieldType())){
            firstCheck = true;
        }
        if(field.isAnnotationPresent(PresentationDate.class)){
            firstCheck = true;
        }
        if(firstCheck){
            Class fieldJavaType = field.getType();
            if(Date.class.equals(fieldJavaType) || Long.class.equals(fieldJavaType)){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        if (fits(field, fieldMetadata)) {
            DateFieldMetadataFacet dateFieldFacet = null;
            PresentationDate presentationBoolean = field.getDeclaredAnnotation(PresentationDate.class);
            boolean useJavaDate = Date.class.equals(field.getType());
            DateModel model = DateModel.DateTime;
            DateCellModel cellModel = DateCellModel.DateAndTime;
            if (presentationBoolean != null) {
                model = presentationBoolean.model();
                cellModel = presentationBoolean.cellModel();
            }
            dateFieldFacet = new DateFieldMetadataFacet(model, cellModel, useJavaDate);
            if(dateFieldFacet != null){
                fieldMetadata.addFacet(dateFieldFacet);
                fieldMetadata.setTargetMetadataType(DateFieldMetadata.class);
                return ProcessResult.HANDLED;
            }
        }
        return ProcessResult.INAPPLICABLE;
    }
}
