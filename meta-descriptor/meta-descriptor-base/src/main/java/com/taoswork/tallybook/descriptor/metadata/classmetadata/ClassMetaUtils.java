package com.taoswork.tallybook.descriptor.metadata.classmetadata;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.*;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public class ClassMetaUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassMetaUtils.class);
    private static Set<Class> NO_REF_FIELD_META;
    static {
        NO_REF_FIELD_META = new HashSet<Class>();
        NO_REF_FIELD_META.add(StringFieldMeta.class);
        NO_REF_FIELD_META.add(PaleFieldMeta.class);
        NO_REF_FIELD_META.add(BooleanFieldMeta.class);
        NO_REF_FIELD_META.add(DateFieldMeta.class);
        NO_REF_FIELD_META.add(EnumFieldMeta.class);
    }

    //Usually used by query filter
    public static IFieldMeta getRoutedFieldMeta(IClassMeta classMeta, String propertyPath) {
        int dpos = propertyPath.indexOf(".");
        if (dpos < 0) {
            IFieldMeta fieldMeta = classMeta.getFieldMeta(propertyPath);
            return fieldMeta;
        }
        String currentPiece = propertyPath.substring(0, dpos);
        String remainPiece = propertyPath.substring(dpos + 1);

        IFieldMeta fieldMeta = classMeta.getFieldMeta(currentPiece);
        if (fieldMeta instanceof ForeignEntityFieldMeta) {
            ForeignEntityFieldMeta foreignEntityFieldMeta = (ForeignEntityFieldMeta) fieldMeta;
            Class entityTypeName = foreignEntityFieldMeta.getTargetType();
            IClassMeta subCm = classMeta.getReferencingClassMeta(entityTypeName);
            return getRoutedFieldMeta(subCm, remainPiece);
        } else if (fieldMeta instanceof EmbeddedFieldMeta) {
            EmbeddedFieldMeta embeddedFieldMeta = (EmbeddedFieldMeta) fieldMeta;
            IClassMeta subCm = embeddedFieldMeta.getClassMetadata();
            return getRoutedFieldMeta(subCm, remainPiece);
        } else {
            LOGGER.error("Get Routed FieldMeta of '{}' not handled.", fieldMeta.getName());
            return null;
        }
    }

    public static Collection<Class> calcReferencedTypes(IClassMeta classMeta) {
        Set<Class> entities = new HashSet<Class>();
        calcReferencedTypes(classMeta, entities);
        return entities;

    }

    public static void calcReferencedTypes(IClassMeta classMeta, Collection<Class> entities) {
        Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
            IFieldMeta fieldMeta = fieldMetaEntry.getValue();
            if (fieldMeta.getIgnored()) {
                continue;
            }
            if(NO_REF_FIELD_META.contains(fieldMeta.getClass())){
                continue;
            }
            fieldMeta.gatherReferencingTypes(entities);
            if (fieldMeta instanceof EmbeddedFieldMeta) {
                EmbeddedFieldMeta typedFieldMeta = (EmbeddedFieldMeta) fieldMeta;
                calcReferencedTypes(typedFieldMeta.getClassMetadata(), entities);
//            } else if (fieldMeta instanceof CollectionFieldMeta) {
//                Class entryType = ((CollectionFieldMeta) fieldMeta).getPresentationClass();
//                if (entryType != null) {
//                    entities.add(entryType);
//                }
//            } else if (fieldMeta instanceof OldMapFieldMeta) {
//                OldMapFieldMeta typedFieldMeta = (OldMapFieldMeta) fieldMeta;
//                {
//                    Class keyEntryType = typedFieldMeta.getKeyType().getPresentationClass();
//                    if (keyEntryType != null) {
//                        entities.add(keyEntryType);
//                    }
//                }
//                {
//                    Class valEntryType = typedFieldMeta.getValueType().getPresentationClass();
//                    if (valEntryType != null) {
//                        entities.add(valEntryType);
//                    }
//                }
            } else {
//                throw new MetadataException("Filed Metadata Not handled: " + fieldMeta.getClass());
            }
        }
    }
}
