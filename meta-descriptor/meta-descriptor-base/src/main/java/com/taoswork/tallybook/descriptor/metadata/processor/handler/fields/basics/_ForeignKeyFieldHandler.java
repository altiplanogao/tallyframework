package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.basics;

import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationForeignKey;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;

import java.lang.reflect.Field;

public class _ForeignKeyFieldHandler extends BaseFieldHandler {

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        PresentationForeignKey pfk = field.getDeclaredAnnotation(PresentationForeignKey.class);
        IFacet facet = metaMediate.getFacet(ForeignEntityFieldMeta.Facet.class);
        if (pfk == null && facet == null) {
            return false;
        }
        return true;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        ForeignEntityFieldMeta.Facet facet = ForeignEntityFieldMeta.Facet.makeFacetByAnnotation(field);
        if(facet == null){
            facet = metaMediate.getFacet(ForeignEntityFieldMeta.Facet.class);
        }
        if(facet == null){
            MetadataException.throwFieldConfigurationException(field);
            return ProcessResult.FAILED;
        }
        if (facet.getTargetEntity() != null) {
            Class fieldType = field.getType();
            IFieldMetaSeed seed = new ForeignEntityFieldMeta.Seed(fieldType, facet);
            metaMediate.setMetaSeed(seed);
            return ProcessResult.HANDLED;
        }

        return ProcessResult.FAILED;
    }
}
