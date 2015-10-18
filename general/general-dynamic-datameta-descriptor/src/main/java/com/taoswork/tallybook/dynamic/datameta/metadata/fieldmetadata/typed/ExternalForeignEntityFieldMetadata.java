package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ExternalForeignEntityFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ExternalForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalForeignEntityFieldMetadata.class);

    private final Class entityType;
    private final String entityFieldName;
    private transient Field entityField;

    public ExternalForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ExternalForeignEntityFieldFacet foreignFieldFacet = (ExternalForeignEntityFieldFacet) intermediate.getFacet(FieldFacetType.ExternalForeignEntity);
        this.entityType = foreignFieldFacet.targetType;
        this.entityFieldName = foreignFieldFacet.realTargetField;
    }

    public Class getEntityType() {
        return entityType;
    }

    public String getEntityFieldName() {
        return entityFieldName;
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
    }

    public Field getEntityField() {
        synchronized (this) {
            if (this.entityField == null) {
                try {
                    String declaringClassName = this.basicFieldMetadataObject.getDeclaringClassName();                   Class ownerClz = Class.forName(declaringClassName);
                    Field field = ownerClz.getDeclaredField(this.entityFieldName);
                    field.setAccessible(true);
                    this.entityField = (field);
                } catch (ClassNotFoundException e) {
                    LOGGER.error("Field declaring class not found");
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    LOGGER.error("Field not found");
                    throw new RuntimeException(e);
                }
            }
            return this.entityField;
        }
    }
}
