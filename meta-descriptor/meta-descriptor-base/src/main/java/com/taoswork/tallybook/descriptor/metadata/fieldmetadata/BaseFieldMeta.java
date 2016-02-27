package com.taoswork.tallybook.descriptor.metadata.fieldmetadata;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

abstract class BaseFieldMeta implements IFieldMeta, Serializable {

    protected final BasicFieldMetaObject basicFieldMetaObject;

    private BaseFieldMeta() {
        basicFieldMetaObject = null;
    }

    public BaseFieldMeta(BasicFieldMetaObject bfmo) {
        if (FieldType.UNKNOWN.equals(bfmo.getFieldType())) {
            bfmo.setFieldType(overrideUnknownFieldType());
        }
        this.basicFieldMetaObject = SerializationUtils.clone(bfmo);
    }

    protected abstract FieldType overrideUnknownFieldType();

    @Override
    public String getTabName() {
        return basicFieldMetaObject.getTabName();
    }

    @Override
    public String getGroupName() {
        return basicFieldMetaObject.getGroupName();
    }

    @Override
    public FieldType getFieldType() {
        return basicFieldMetaObject.getFieldType();
    }

    @Override
    public boolean isEditable() {
        return basicFieldMetaObject.isEditable();
    }

    @Override
    public boolean isRequired() {
        return basicFieldMetaObject.isRequired();
    }

    @Override
    public int getVisibility() {
        return basicFieldMetaObject.getVisibility();
    }

    @Override
    public Field getField() {
        return basicFieldMetaObject.getField();
    }

    @Override
    public int getOrder() {
        return basicFieldMetaObject.getOrder();
    }

    @Override
    public String getName() {
        return basicFieldMetaObject.getName();
    }

    @Override
    public String getFriendlyName() {
        return basicFieldMetaObject.getFriendlyName();
    }

    @Override
    public boolean getIgnored() {
        return basicFieldMetaObject.getIgnored();
    }

    @Override
    public Class<? extends IFieldGate> getFieldValueGateOverride() {
        return basicFieldMetaObject.getFieldValueGateOverride();
    }

    @Override
    public boolean getSkipDefaultFieldValueGate() {
        return basicFieldMetaObject.isSkipDefaultFieldValueGate();
    }

    @Override
    public Class getFieldClass() {
        return basicFieldMetaObject.getFieldClass();
    }
}
