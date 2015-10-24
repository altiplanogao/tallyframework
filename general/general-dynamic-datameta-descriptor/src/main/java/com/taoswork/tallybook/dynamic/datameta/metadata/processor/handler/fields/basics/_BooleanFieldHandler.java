package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.BooleanFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.BooleanFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationBoolean;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _BooleanFieldHandler implements IFieldHandler {
    private boolean fits(Field field) {
        if (Boolean.class.equals(field.getType())) {
            return true;
        } else if (boolean.class.equals(field.getType())) {
            return true;
        }
        return false;
    }

    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        if (fits(field)) {
            BooleanFieldMetadataFacet booleanFieldFacet = null;
            PresentationBoolean presentationBoolean = field.getDeclaredAnnotation(PresentationBoolean.class);
            if (presentationBoolean != null) {
                booleanFieldFacet = new BooleanFieldMetadataFacet(presentationBoolean.model());
            } else {
                booleanFieldFacet = new BooleanFieldMetadataFacet();
            }
            fieldMetadata.addFacet(booleanFieldFacet);
            fieldMetadata.setTargetMetadataType(BooleanFieldMetadata.class);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
