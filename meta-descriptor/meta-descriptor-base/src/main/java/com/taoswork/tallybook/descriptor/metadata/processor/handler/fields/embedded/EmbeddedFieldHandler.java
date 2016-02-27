package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.embedded;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ImmutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class EmbeddedFieldHandler extends BaseFieldHandler {
    private final IClassHandler classProcessor;

    public EmbeddedFieldHandler(IClassHandler classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        EmbeddedFieldMeta.Facet facet = metaMediate.getFacet(EmbeddedFieldMeta.Facet.class);
        return facet != null;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        EmbeddedFieldMeta.Facet facet = metaMediate.getFacet(EmbeddedFieldMeta.Facet.class);
        if (facet != null) {
            Class<?> embeddedType = field.getType();
            IClassMeta embeddedMetadata = generateEmbeddedClassMetadataWithoutReferencing(classProcessor, embeddedType);
            if (embeddedMetadata != null) {
                facet.setEmbeddedMetadata(embeddedMetadata);
                EmbeddedFieldMeta.Seed seed = new EmbeddedFieldMeta.Seed(facet);
                metaMediate.setMetaSeed(seed);
                return ProcessResult.HANDLED;
            } else {
                throw new MetadataException("Should not fail.");
            }
        }
        return ProcessResult.INAPPLICABLE;
    }

    public static IClassMeta generateEmbeddedClassMetadataWithoutReferencing(IClassHandler classProcessor, Class embeddableClass) {
        MutableClassMeta embeddedMetadata = new MutableClassMeta(embeddableClass);
        embeddedMetadata.publishReferencingClassMetadatas(null);
        ProcessResult innerResult = classProcessor.process(embeddableClass, embeddedMetadata);
        if (innerResult == ProcessResult.HANDLED) {
            return new ImmutableClassMeta(embeddedMetadata);
        } else {
            return null;
        }
    }
}
