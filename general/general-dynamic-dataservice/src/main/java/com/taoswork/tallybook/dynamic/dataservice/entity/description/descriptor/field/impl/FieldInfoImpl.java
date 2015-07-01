package com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.impl;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class FieldInfoImpl
        extends NamedInfoImpl
        implements FieldInfoRW {

    public int visibility = Visibility.DEFAULT;
    public boolean nameField = false;
    private boolean isCollection;
    private SupportedFieldType fieldType = SupportedFieldType.UNKNOWN;

    public FieldInfoImpl(){

    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean isGridVisible() {
        return Visibility.gridVisible(visibility);
    }

    @Override
    public boolean isCollection() {
        return isCollection;
    }

    @Override
    public boolean isNameField() {
        return nameField;
    }

    @Override
    public void setNameField(boolean nameField) {
        this.nameField = nameField;
    }

    @Override
    public SupportedFieldType getFieldType() {
        if (fieldType == null) {
            return SupportedFieldType.UNKNOWN;
        }
        return fieldType;
    }

    @Override
    public boolean isIdField() {
        return getFieldType().equals(SupportedFieldType.ID);
    }

    @Override
    public void setFieldType(SupportedFieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public void merge(FieldInfo another) {
        if (this.equals(another)) {
            return;
        }
        //TODO
    }

    @Override
    public NamedInfoImpl setOrder(int order) {
        return super.setOrder(order);
    }
}
