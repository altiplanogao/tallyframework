package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class MapFieldMetadataFacet extends CollectionFieldMetadataFacetBase {
    private final Class _keyType;
    private final Class _valueType;

    private final EntryTypeUnion keyType;
    private final EntryTypeUnion valueType;

    private final Class mapType;

    public MapFieldMetadataFacet(Class mapType,
                                 Class keyType, ClassMetadata keyEmbeddedClassMetadata,
                                 Class valueType, ClassMetadata valueEmbeddedClassMetadata) {
        super();
        this.mapType = mapType;

        this._keyType = keyType;
        this._valueType = valueType;

        this.keyType = new EntryTypeUnion(keyType, keyEmbeddedClassMetadata);
        this.valueType = new EntryTypeUnion(valueType, valueEmbeddedClassMetadata);
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Map;
    }

    public EntryTypeUnion getKeyType() {
        return this.keyType;
    }

    public EntryTypeUnion getValueType() {
        return this.valueType;
    }

    public Class getMapType() {
        return mapType;
    }
}
