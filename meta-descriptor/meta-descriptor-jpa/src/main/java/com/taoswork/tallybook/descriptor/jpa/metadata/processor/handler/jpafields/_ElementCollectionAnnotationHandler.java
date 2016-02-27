package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.PrimitiveEntries;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.BasicListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFacet;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.PrimitiveListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list.CollectionHandlerBaseField;
import com.taoswork.tallybook.general.solution.Tristate;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;

import javax.persistence.ElementCollection;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class _ElementCollectionAnnotationHandler
        implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        ElementCollection ec = field.getDeclaredAnnotation(ElementCollection.class);
        if (ec != null) {
            Class collectionType = field.getType();
            if (Collection.class.isAssignableFrom(collectionType)) {
                processInTypeCaseCollection(field, metaMediate);
            } else if (Map.class.isAssignableFrom(collectionType)) {
                processInTypeCaseMap(field, metaMediate);
            }
            return ProcessResult.PASSING_THROUGH;
        }
        return ProcessResult.INAPPLICABLE;
    }

    private void processInTypeCaseMap(Field field, FieldMetaMediate metaMediate) {
        ElementCollection ec = field.getDeclaredAnnotation(ElementCollection.class);
        MapField mf = field.getDeclaredAnnotation(MapField.class);
        if (mf == null)
            MetadataException.throwFieldConfigurationException(field, "@MapField should be annotated for @ElementCollection");
        if (!MapMode.Basic.equals(mf.mode())) {
            MetadataException.throwFieldConfigurationException(field, "for @ElementCollection, @MapField.mode() == Basic");
        }
    }

    private void processInTypeCaseCollection(Field field, FieldMetaMediate metaMediate) {
        ElementCollection ec = field.getDeclaredAnnotation(ElementCollection.class);
        ;
        Class targetClz = ec.targetClass();
        if (targetClz.equals(void.class)) {
            targetClz = GenericTypeUtility.getGenericArgument(field, 0);
        }

        CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
        final Tristate isPrimitive = CollectionHandlerBaseField.isPrimitiveType(targetClz);
        if (cf != null) {
            ListFacet facet = ListFacet.byAnnotation(field);
            //Check conflict
            CollectionMode cm = cf.mode();
            if (!targetClz.equals(facet.getEntryTargetClass())) {
                MetadataException.throwFieldConfigurationException(field,
                        "targetClass un-match in @ElementCollection & @CollectionField " + targetClz + " vs " + cf.targetClass());
            }
            switch (cm) {
                case Primitive: {
                    if (Tristate.False.equals(isPrimitive)) {
                        MetadataException.throwFieldConfigurationException(field);
                    }
                    break;
                }
                case Basic: {
                    if (Tristate.True.equals(isPrimitive)) {
                        MetadataException.throwFieldConfigurationException(field);
                    }
                    break;
                }
                default:
                    MetadataException.throwFieldConfigurationException(field, "Only Primitive/Basic expected");
            }
        } else {
            switch (isPrimitive) {
                case True: {
                    Class<? extends IPrimitiveEntry> defPrimEntry = PrimitiveEntries.getDefaultPrimitiveEntry(targetClz);
                    if (defPrimEntry != null) {
                        PrimitiveListFieldMeta.Facet facet =
                                new PrimitiveListFieldMeta.Facet(field, false, targetClz, defPrimEntry);
                        metaMediate.pushFacet(facet);
                    } else {
                        MetadataException.throwFieldConfigurationException(field, "Please specify @CollectionField with IPrimitiveEntry");
                    }
                    break;
                }
                case False: {
                    BasicListFieldMeta.Facet facet = new BasicListFieldMeta.Facet(field, false, targetClz);
                    metaMediate.pushFacet(facet);
                    break;
                }
                case Unsure:
                    MetadataException.throwFieldConfigurationException(field, "Please specify @CollectionField");
                    break;
                default:
                    MetadataException.throwFieldConfigurationException(field, "Unexpected enum");
            }
        }
    }
}
