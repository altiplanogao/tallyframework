package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.embedded;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.EmbeddedFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.IFieldHandler;

import javax.persistence.Embedded;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EmbeddedFieldHandler implements IFieldHandler {
    private final ClassProcessor classProcessor;

    public EmbeddedFieldHandler(ClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public ProcessResult process(Field field, FieldMetadataIntermediate fieldMetadata) {
        Embedded embedded = field.getDeclaredAnnotation(Embedded.class);
        if (embedded != null) {
            Class<?> embeddedType = field.getType();
            ClassMetadata embeddedMetadata = FieldMetadataHelper.generateEmbeddedClassMetadata(classProcessor, embeddedType);
            if (embeddedMetadata != null) {
                EmbeddedFieldFacet embeddedFieldFacet = new EmbeddedFieldFacet(embeddedMetadata);
                fieldMetadata.addFacet(embeddedFieldFacet);
                fieldMetadata.setTargetMetadataType(EmbeddedFieldMetadata.class);
                return ProcessResult.HANDLED;
            } else {
                throw new IllegalStateException("Should not fail.");
            }
        }
        return ProcessResult.INAPPLICABLE;
    }
}
