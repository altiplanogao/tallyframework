package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IFieldValueGate;
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
        BasicFieldMetadataObject bfmo = intermediate.getBasicFieldMetadataObject();
        if(FieldType.UNKNOWN.equals(bfmo.getFieldType())){
            bfmo.setFieldType(overrideUnknownFieldType());
        }
        this.basicFieldMetadataObject = SerializationUtils.clone(bfmo);
    }

    protected abstract FieldType overrideUnknownFieldType();

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
    public boolean isEditable() {
        return basicFieldMetadataObject.isEditable();
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
    public String getName() {
        return basicFieldMetadataObject.getName();
    }

    @Override
    public String getFriendlyName() {
        return basicFieldMetadataObject.getFriendlyName();
    }

    @Override
    public boolean getIgnored() {
        return basicFieldMetadataObject.getIgnored();
    }

    @Override
    public Class<? extends IFieldValueGate> getFieldValueGateOverride() {
        return basicFieldMetadataObject.getFieldValueGateOverride();
    }

    @Override
    public boolean getSkipDefaultFieldValueGate() {
        return basicFieldMetadataObject.isSkipDefaultFieldValueGate();
    }

    @Override
    public Class getFieldClass() {
        return basicFieldMetadataObject.getFieldClass();
    }
}
