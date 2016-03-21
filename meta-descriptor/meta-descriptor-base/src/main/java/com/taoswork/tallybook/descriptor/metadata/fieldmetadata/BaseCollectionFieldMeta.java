package com.taoswork.tallybook.descriptor.metadata.fieldmetadata;

import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;

public abstract class BaseCollectionFieldMeta extends BaseFieldMeta {

    public BaseCollectionFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

//    @Override
//    public boolean isPrimitiveField() {
//        return false;
//    }

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
        throw new MetadataException("Should never be called");
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
