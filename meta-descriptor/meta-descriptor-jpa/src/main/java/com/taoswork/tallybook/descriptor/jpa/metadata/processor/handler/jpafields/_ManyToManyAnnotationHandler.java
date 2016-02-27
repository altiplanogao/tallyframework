package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.AdornedLookupListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFacet;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.LookupListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.ManyToMany;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/19.
 */
public class _ManyToManyAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        ManyToMany toMany = field.getDeclaredAnnotation(ManyToMany.class);
        if (toMany != null) {
            Class collectionType = field.getType();
            if (Collection.class.isAssignableFrom(collectionType)) {
                processInTypeCollection(field, metaMediate);
            } else if (Map.class.isAssignableFrom(collectionType)){
                processInTypeMap(field, metaMediate);
            }
            return ProcessResult.PASSING_THROUGH;
        }
        return ProcessResult.INAPPLICABLE;
    }

    private void processInTypeMap(Field field, FieldMetaMediate metaMediate) {
        MapField mf = field.getDeclaredAnnotation(MapField.class);
        if (mf == null)
            MetadataException.throwFieldConfigurationException(field, "@MapField should be annotated for @OneToMany");
        switch (mf.mode()){
            case Lookup:
            case AdornedLookup:
                break;
            default:
                MetadataException.throwFieldConfigurationException(field, "for @OneToMany, @MapField.mode() == Lookup or AdornedLookup");
        }
    }

    private void processInTypeCollection(Field field, FieldMetaMediate metaMediate) {
        ManyToMany toMany = field.getDeclaredAnnotation(ManyToMany.class);
        Class toManyTargetEntity = toMany.targetEntity();
        if (toManyTargetEntity == void.class) {
            toManyTargetEntity = field.getType();
        }
        CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
        final ListFacet cfacet;
        if(cf == null){
            MetadataException.throwFieldConfigurationException(field, "@CollectionField needed for @ManyToMany field");
            cfacet = null;
        } else {
            Class pfkTargetEntity = cf.targetClass();
            if (pfkTargetEntity == void.class) {
                pfkTargetEntity = field.getType();
            }
            if (!pfkTargetEntity.equals(toManyTargetEntity)) {
                MetadataException.throwFieldConfigurationException(field, "Conflict configuration.");
            }
            cfacet = ListFacet.byAnnotation(field);
        }
        switch (cf.mode()){
            case Lookup: {
                LookupListFieldMeta.Facet facet = new LookupListFieldMeta.Facet(cfacet);
                metaMediate.pushFacet(facet);
                break;
            }
            case AdornedLookup: {
                Class joinEntity = cf.joinEntity();
                if(joinEntity.equals(void.class)){
                    MetadataException.throwFieldConfigurationException(field, "joinEntity not defined.");
                }
                AdornedLookupListFieldMeta.Facet facet = new AdornedLookupListFieldMeta.Facet(cfacet);
                metaMediate.pushFacet(facet);
                break;
            }
            default:
                MetadataException.throwFieldConfigurationException(field, "Conflict configuration.");
        }
    }
}
