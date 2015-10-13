package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.BooleanFieldMetaFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanField;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _BooleanFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        if(Boolean.class.equals(field.getType())){
            BooleanFieldMetaFacet booleanFieldMetaFacet = null;
            BooleanField booleanField = field.getDeclaredAnnotation(BooleanField.class);
            if(booleanField != null){
                booleanFieldMetaFacet = new BooleanFieldMetaFacet(booleanField.model());
            }else {
                booleanFieldMetaFacet = new BooleanFieldMetaFacet();
            }
            fieldMetadata.addFacet(booleanFieldMetaFacet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
