package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.basic.ExternalForeignEntityFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseNonCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public final class ExternalForeignEntityFieldMetadata extends BaseNonCollectionFieldMetadata implements IFieldMetadata {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalForeignEntityFieldMetadata.class);

    private final Class declareType;
    private final Class targetType;
    private final String theDataFieldName;
    private final String displayProperty;
    private final String idProperty;
    private transient Field entityField;

    public ExternalForeignEntityFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        ExternalForeignEntityFieldMetadataFacet foreignFieldFacet = (ExternalForeignEntityFieldMetadataFacet) intermediate.getFacet(FieldFacetType.ExternalForeignEntity);
        this.declareType = foreignFieldFacet.declaredType;
        this.targetType = foreignFieldFacet.targetType;
        this.theDataFieldName = foreignFieldFacet.theDataField;
        this.displayProperty = foreignFieldFacet.displayField;
        this.idProperty = foreignFieldFacet.idProperty;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.EXTERNAL_FOREIGN_KEY;
    }

    public Class getDeclareType() {
        return declareType;
    }

    public Class getTargetType() {
        return targetType;
    }

    public String getTheDataFieldName() {
        return theDataFieldName;
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
                    Field field = ownerClz.getDeclaredField(this.theDataFieldName);
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

    public String getIdProperty() {
        return idProperty;
    }

    public String getDisplayProperty() {
        return displayProperty;
    }
}
