package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

public abstract class BaseCollectionFieldMetadata extends BaseFieldMetadata {
    public BaseCollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
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

    public abstract Class getPresentationClass();

    public abstract Class getPresentationCeilingClass();
}
