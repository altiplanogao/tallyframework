package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.BasicFieldMetaFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class BasicFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        BasicFieldMetaFacet columnFacet = new BasicFieldMetaFacet();
        Id idAnnotation = field.getDeclaredAnnotation(Id.class);
        if (idAnnotation!= null) {
            return ProcessResult.INAPPLICABLE;
        }

        PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
        if(presentationField != null &&
            presentationField.fieldType() == FieldType.ENUMERATION &&
            presentationField.enumeration() != Void.class){
            return ProcessResult.INAPPLICABLE;
        }

        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (null != columnAnnotation) {
            boolean nullable = columnAnnotation.nullable();
            columnFacet.setRequired(!nullable);

            int length = columnAnnotation.length();
            columnFacet.setLength(length);

            fieldMetadata.addFacet(columnFacet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
