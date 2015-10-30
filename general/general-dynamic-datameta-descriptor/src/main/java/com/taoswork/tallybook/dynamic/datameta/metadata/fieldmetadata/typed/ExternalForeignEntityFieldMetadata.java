package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ExternalForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BasicFieldMetadataObject;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ExternalForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalForeignEntityFieldMetadata.class);

    private final Class entityType;
    private final String entityFieldName;
    private final String entityDisplayProperty;
    private final String entityIdProperty;
    private transient Field entityField;

    public ExternalForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ExternalForeignEntityFieldMetadataFacet foreignFieldFacet = (ExternalForeignEntityFieldMetadataFacet) intermediate.getFacet(FieldFacetType.ExternalForeignEntity);
        this.entityType = foreignFieldFacet.targetType;
        this.entityFieldName = foreignFieldFacet.realTargetField;
        this.entityDisplayProperty = foreignFieldFacet.displayField;
        this.entityIdProperty = foreignFieldFacet.idProperty;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.EXTERNAL_FOREIGN_KEY;
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

    public String getEntityIdProperty() {
        return entityIdProperty;
    }

    public String getEntityDisplayProperty() {
        return entityDisplayProperty;
    }
}
