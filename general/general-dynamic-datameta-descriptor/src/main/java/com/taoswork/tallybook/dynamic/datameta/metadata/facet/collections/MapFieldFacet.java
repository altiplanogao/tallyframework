package com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.fields.FieldMetadataHelper;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class MapFieldFacet extends CollectionFieldFacetBase {
    private final Class _keyType;
    private final Class _valueType;

    private final Class keyBasicType;
    private final ClassMetadata keyEmbeddedClassMetadata;
    private final Class keyEntityType;

    private final Class valueBasicType;
    private final ClassMetadata valueEmbeddedClassMetadata;
    private final Class valueEntityType;

    public MapFieldFacet(Class keyType, ClassMetadata keyEmbeddedClassMetadata,
                         Class valueType, ClassMetadata valueEmbeddedClassMetadata) {
        super();
        this._keyType = keyType;
        this._valueType = valueType;

        if (FieldMetadataHelper.isEmbeddable(_keyType)) {
            this.keyBasicType = null;
            this.keyEmbeddedClassMetadata = keyEmbeddedClassMetadata;
            this.keyEntityType = null;
        } else if (FieldMetadataHelper.isEntity(_keyType)) {
            this.keyBasicType = null;
            this.keyEmbeddedClassMetadata = null;
            this.keyEntityType = _keyType;
        } else {
            this.keyBasicType = _keyType;
            this.keyEmbeddedClassMetadata = null;
            this.keyEntityType = null;
        }

        if (FieldMetadataHelper.isEmbeddable(_valueType)) {
            this.valueBasicType = null;
            this.valueEmbeddedClassMetadata = valueEmbeddedClassMetadata;
            this.valueEntityType = null;
        } else if (FieldMetadataHelper.isEntity(_valueType)) {
            this.valueBasicType = null;
            this.valueEmbeddedClassMetadata = null;
            this.valueEntityType = _valueType;
        } else {
            this.valueBasicType = _valueType;
            this.valueEmbeddedClassMetadata = null;
            this.valueEntityType = null;
        }
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Map;
    }

    public Class getKeyBasicType() {
        return keyBasicType;
    }

    public ClassMetadata getKeyEmbeddedClassMetadata() {
        return keyEmbeddedClassMetadata;
    }

    public Class getKeyEntityType() {
        return keyEntityType;
    }

    public Class getValueBasicType() {
        return valueBasicType;
    }

    public ClassMetadata getValueEmbeddedClassMetadata() {
        return valueEmbeddedClassMetadata;
    }

    public Class getValueEntityType() {
        return valueEntityType;
    }
}
