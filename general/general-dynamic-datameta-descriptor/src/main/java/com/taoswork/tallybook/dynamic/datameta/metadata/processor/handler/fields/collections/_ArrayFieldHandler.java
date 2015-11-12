package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.ArrayFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.PresentationCollection;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _ArrayFieldHandler extends _1DCollectionFieldHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(_ArrayFieldHandler.class);
    private final ClassProcessor classProcessor;

    public _ArrayFieldHandler(ClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadataIntermediate fieldMetadata) {
        Class type = field.getType();
        if (Object[].class.isAssignableFrom(type)) {
            throw new IllegalAccessError("Array Type not supported");
        }
        return ProcessResult.INAPPLICABLE;
    }
}
