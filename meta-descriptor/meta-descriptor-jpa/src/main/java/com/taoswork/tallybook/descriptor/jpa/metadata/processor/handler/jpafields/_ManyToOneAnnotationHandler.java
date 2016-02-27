package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationForeignKey;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.ManyToOne;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/19.
 */
public class _ManyToOneAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        ManyToOne toOne = field.getDeclaredAnnotation(ManyToOne.class);
        if(toOne != null){
            Class toOneTargetEntity = toOne.targetEntity();
            if(toOneTargetEntity == void.class){
                toOneTargetEntity = field.getType();
            }
            PresentationForeignKey pfk = field.getDeclaredAnnotation(PresentationForeignKey.class);
            if(pfk != null){
                Class pfkTargetEntity = pfk.targetEntity();
                if(pfkTargetEntity == void.class){
                    pfkTargetEntity = field.getType();
                }
                if(!pfkTargetEntity.equals(toOneTargetEntity)){
                    MetadataException.throwFieldConfigurationException(field);
                }
            }
            ForeignEntityFieldMeta.Facet facet = new ForeignEntityFieldMeta.Facet(toOneTargetEntity);
            metaMediate.pushFacet(facet);
            return ProcessResult.PASSING_THROUGH;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
