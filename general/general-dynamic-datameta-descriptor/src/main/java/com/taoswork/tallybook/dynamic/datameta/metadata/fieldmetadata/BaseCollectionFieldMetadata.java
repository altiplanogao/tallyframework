package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.property.Property;

public abstract class BaseCollectionFieldMetadata extends BaseFieldMetadata {
    public BaseCollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
    }

    @Override
    public boolean isCollectionField() {
        return true;
    }

    @Override
    public boolean isNameField() {
        return false;
    }

    @Override
    public void setNameField(boolean b) {
        throw new IllegalStateException("Should never be called");
    }

    @Override
    final public boolean showOnGrid() {
        return false;
    }

    @Override
    final public boolean isId() {
        return false;
    }

    @Override
    public Property[] getProperties(Object obj) throws IllegalAccessException {
        return new Property[0];
    }
}
