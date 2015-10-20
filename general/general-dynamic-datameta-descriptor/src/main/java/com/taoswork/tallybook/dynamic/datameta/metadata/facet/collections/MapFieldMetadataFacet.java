package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ElementTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class MapFieldMetadataFacet extends CollectionFieldMetadataFacetBase {
    private final Class _keyType;
    private final Class _valueType;

    private final ElementTypeUnion keyType;
    private final ElementTypeUnion valueType;

    private final Class mapType;

    public MapFieldMetadataFacet(Class mapType,
                                 Class keyType, ClassMetadata keyEmbeddedClassMetadata,
                                 Class valueType, ClassMetadata valueEmbeddedClassMetadata) {
        super();
        this.mapType = mapType;

        this._keyType = keyType;
        this._valueType = valueType;

        this.keyType = new ElementTypeUnion(keyType, keyEmbeddedClassMetadata);
        this.valueType = new ElementTypeUnion(valueType, valueEmbeddedClassMetadata);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Map;
    }

    public ElementTypeUnion getKeyType() {
        return this.keyType;
    }

    public ElementTypeUnion getValueType() {
        return this.valueType;
    }

    public Class getMapType() {
        return mapType;
    }
}
