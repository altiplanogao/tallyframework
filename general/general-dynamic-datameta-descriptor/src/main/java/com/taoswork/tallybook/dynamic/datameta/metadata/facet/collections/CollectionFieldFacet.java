package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ElementTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldFacet implements IFieldFacet {
    private final Class _elementType;
    private final Class _targetElementType;

    private final Class collectionType;
    private final ElementTypeUnion elementType;

    public CollectionFieldFacet(Class collectionType, Class elementType, Class targetElementType, ClassMetadata embeddedClassMetadata) {
        this._targetElementType = targetElementType;
        this._elementType = elementType;
        this.collectionType = collectionType;
        this.elementType = new ElementTypeUnion(targetElementType, embeddedClassMetadata);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }

    @Override
    public void merge(IFieldFacet facet) {

    }

    public Class getCollectionType() {
        return collectionType;
    }

    public ElementTypeUnion getElementType() {
        return elementType;
    }
}
