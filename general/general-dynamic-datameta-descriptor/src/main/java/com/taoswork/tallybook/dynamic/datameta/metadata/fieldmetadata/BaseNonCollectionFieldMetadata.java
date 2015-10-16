package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.property.Property;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import java.lang.reflect.Field;

public abstract class BaseNonCollectionFieldMetadata extends BaseFieldMetadata {
    public BaseNonCollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
    }

    @Override
    public boolean isCollectionField() {
        return false;
    }

    @Override
    public boolean isId() {
        return basicFieldMetadataObject.isId();
    }

    @Override
    public boolean isNameField() {
        return basicFieldMetadataObject.isNameField();
    }

    @Override
    public void setNameField(boolean b) {
        basicFieldMetadataObject.setNameField(b);
    }

    @Override
    public boolean showOnGrid() {
        return Visibility.gridVisible(basicFieldMetadataObject.getVisibility());
    }

    @Override
    public Property[] getProperties(Object obj) throws IllegalAccessException {
        Field field = basicFieldMetadataObject.getField();
        Object o = field.get(obj);
        return new Property[]{new Property(field.getName(), o)};
    }
}
