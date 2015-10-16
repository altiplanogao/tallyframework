package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldFacet implements IFieldFacet {
    private final Class _elementType;
    private final Class _targetElementType;

    private final Class basicType;
    private final ClassMetadata embeddedClassMetadata;
    private final Class entityType;

    public CollectionFieldFacet(Class elementType, Class targetElementType, ClassMetadata embeddedClassMetadata) {
        this._targetElementType = targetElementType;
        this._elementType = elementType;
        if (FieldMetadataHelper.isEmbeddable(targetElementType)) {
            this.basicType = null;
            this.embeddedClassMetadata = embeddedClassMetadata;
            this.entityType = null;
        } else if (FieldMetadataHelper.isEntity(targetElementType)) {
            this.basicType = null;
            this.embeddedClassMetadata = null;
            this.entityType = targetElementType;
        } else {
            this.basicType = targetElementType;
            this.embeddedClassMetadata = null;
            this.entityType = null;
        }
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }

    public Class getBasicType() {
        return basicType;
    }

    public ClassMetadata getEmbeddedClassMetadata() {
        return embeddedClassMetadata;
    }

    public Class getEntityType() {
        return entityType;
    }
}
