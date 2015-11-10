package com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionLikeFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public class ClassMetadataUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassMetadataUtils.class);

    //Usually used by query filter
    public static IFieldMetadata getRoutedFieldMetadata(IClassMetadata classMetadata, String propertyPath) {
        int dpos = propertyPath.indexOf(".");
        if (dpos < 0) {
            IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(propertyPath);
            return fieldMetadata;
        }
        String currentPiece = propertyPath.substring(0, dpos);
        String remainPiece = propertyPath.substring(dpos + 1);

        IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(currentPiece);
        if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
            ForeignEntityFieldMetadata foreignEntityFieldMetadata = (ForeignEntityFieldMetadata) fieldMetadata;
            Class entityTypeName = foreignEntityFieldMetadata.getEntityType();
            IClassMetadata subCm = classMetadata.getReferencingClassMetadata(entityTypeName);
            return getRoutedFieldMetadata(subCm, remainPiece);
        } else if (fieldMetadata instanceof EmbeddedFieldMetadata) {
            EmbeddedFieldMetadata embeddedFieldMetadata = (EmbeddedFieldMetadata) fieldMetadata;
            IClassMetadata subCm = embeddedFieldMetadata.getClassMetadata();
            return getRoutedFieldMetadata(subCm, remainPiece);
        } else {
            LOGGER.error("Get Routed FieldMetadata of '{}' not handled.", fieldMetadata.getName());
            return null;
        }
    }

    public static Collection<Class> calcReferencedTypes(IClassMetadata classMetadata) {
        Set<Class> entities = new HashSet<Class>();
        calcReferencedTypes(classMetadata, entities);
        return entities;

    }

    public static void calcReferencedTypes(IClassMetadata classMetadata, Collection<Class> entities) {
        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            if (fieldMetadata.getIgnored()) {
                continue;
            }
            if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                entities.add(((ForeignEntityFieldMetadata) fieldMetadata).getEntityType());
            } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
                //add or not?
                entities.add(((ExternalForeignEntityFieldMetadata) fieldMetadata).getEntityType());
            } else if (fieldMetadata instanceof EmbeddedFieldMetadata) {
                EmbeddedFieldMetadata typedFieldMetadata = (EmbeddedFieldMetadata)fieldMetadata;
                calcReferencedTypes(typedFieldMetadata.getClassMetadata(), entities);
            } else if (fieldMetadata instanceof CollectionLikeFieldMetadata) {
                Class entryType = ((CollectionLikeFieldMetadata) fieldMetadata).getEntryTypeUnion().getPresentationClass();
                if (entryType != null) {
                    entities.add(entryType);
                }
            } else if (fieldMetadata instanceof MapFieldMetadata) {
                MapFieldMetadata typedFieldMetadata = (MapFieldMetadata) fieldMetadata;
                {
                    Class keyEntryType = typedFieldMetadata.getKeyType().getPresentationClass();
                    if (keyEntryType != null) {
                        entities.add(keyEntryType);
                    }
                }
                {
                    Class valEntryType = typedFieldMetadata.getValueType().getPresentationClass();
                    if (valEntryType != null) {
                        entities.add(valEntryType);
                    }
                }
            }
        }
    }
}
