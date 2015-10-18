package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.FriendlyMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendlyOrdered;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

abstract class BaseFieldMetadata implements IFieldMetadata, Serializable {

    protected final BasicFieldMetadataObject basicFieldMetadataObject;

    private BaseFieldMetadata() {
        basicFieldMetadataObject = null;
    }

    public BaseFieldMetadata(FieldMetadataIntermediate intermediate) {
        this.basicFieldMetadataObject = SerializationUtils.clone(intermediate.getBasicFieldMetadataObject());
    }

    @Override
    public String getTabName() {
        return basicFieldMetadataObject.getTabName();
    }

    @Override
    public String getGroupName() {
        return basicFieldMetadataObject.getGroupName();
    }

    @Override
    public FieldType getFieldType() {
        return basicFieldMetadataObject.getFieldType();
    }

    @Override
    public boolean isRequired() {
        return basicFieldMetadataObject.isRequired();
    }

    @Override
    public int getVisibility() {
        return basicFieldMetadataObject.getVisibility();
    }

    @Override
    public Field getField() {
        return basicFieldMetadataObject.getField();
    }

    @Override
    public int getOrder() {
        return basicFieldMetadataObject.getOrder();
    }

    @Override
    public IFriendlyOrdered setOrder(int order) {
        return basicFieldMetadataObject.setOrder(order);
    }

    @Override
    public String getName() {
        return basicFieldMetadataObject.getName();
    }

    @Override
    public FriendlyMetadata setName(String name) {
        return basicFieldMetadataObject.setName(name);
    }

    @Override
    public String getFriendlyName() {
        return basicFieldMetadataObject.getFriendlyName();
    }

    @Override
    public FriendlyMetadata setFriendlyName(String friendlyName) {
        return basicFieldMetadataObject.setFriendlyName(friendlyName);
    }

    @Override
    public Class getFieldClass() {
        return basicFieldMetadataObject.getFieldClass();
    }
}