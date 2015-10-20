package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ElementTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class CollectionFieldMetadataFacet implements IFieldMetadataFacet {
    private final Class _elementType;
    private final Class _targetElementType;

    private final Class collectionType;
    private final ElementTypeUnion elementType;

    public CollectionFieldMetadataFacet(Class collectionType, Class elementType, Class targetElementType, ClassMetadata embeddedClassMetadata) {
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
    public void merge(IFieldMetadataFacet facet) {

    }

    public Class getCollectionType() {
        return collectionType;
    }

    public ElementTypeUnion getElementType() {
        return elementType;
    }
}
