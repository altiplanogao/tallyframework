package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFacet;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.EntityListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.EntityMapFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/19.
 */
public class _OneToManyAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        OneToMany toOne = field.getDeclaredAnnotation(OneToMany.class);
        if (toOne != null) {
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

    private void processInTypeCollection(Field field, FieldMetaMediate metaMediate) {
        OneToMany toMany = field.getDeclaredAnnotation(OneToMany.class);
        Class toManyTargetEntity = toMany.targetEntity();
        if (toManyTargetEntity == void.class) {
            toManyTargetEntity = field.getType();
        }
        String mappedBy = toMany.mappedBy();

        CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
        final ListFacet cfacet;
        if (cf != null) {
            Class pfkTargetEntity = cf.targetClass();
            if (pfkTargetEntity == void.class) {
                pfkTargetEntity = field.getType();
            }
            String cfMappedBy = cf.mappedBy();
            if(!CollectionMode.Entity.equals(cf.mode())){
                MetadataException.throwFieldConfigurationException(field, "Conflict configuration.");
            }
            if (!pfkTargetEntity.equals(toManyTargetEntity)) {
                MetadataException.throwFieldConfigurationException(field, "Conflict configuration.");
            }
            if(!mappedBy.equals(cfMappedBy)){
                MetadataException.throwFieldConfigurationException(field, "Conflict configuration.");
            }
            cfacet = ListFacet.byAnnotation(field);
        } else {
            cfacet = new ListFacet(field, false, null);
        }
        cfacet.setEntryTargetClass(toManyTargetEntity);
        EntityListFieldMeta.Facet facet = new EntityListFieldMeta.Facet(cfacet);
        facet.setMappedBy(mappedBy);
        metaMediate.pushFacet(facet);
    }

    private void processInTypeMap(Field field, FieldMetaMediate metaMediate) {
        OneToMany toMany = field.getDeclaredAnnotation(OneToMany.class);
        MapField mf = field.getDeclaredAnnotation(MapField.class);
        if (mf == null){
            Class toManyTarget = toMany.targetEntity();
            EntityMapFieldMeta.Facet facet = new EntityMapFieldMeta.Facet(field, false, null);
            if(!void.class.equals(toManyTarget)){
                facet.setValueTargetType(toManyTarget);
            }
            metaMediate.pushFacet(facet);
        }else {
            if (!MapMode.Entity.equals(mf.mode())) {
                MetadataException.throwFieldConfigurationException(field, "for @OneToMany, @MapField.mode() == Entity");
            }
        }
    }
}
