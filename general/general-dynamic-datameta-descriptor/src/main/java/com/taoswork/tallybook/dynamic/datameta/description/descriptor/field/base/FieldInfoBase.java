package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
abstract class FieldInfoBase
    extends NamedOrderedInfoImpl
    implements IFieldInfoRW {

    public int visibility = Visibility.DEFAULT;
    public boolean required = false;

    private FieldType fieldType = FieldType.UNKNOWN;

    public FieldInfoBase(String name, String friendlyName) {
        super(name, friendlyName);
        visibility = Visibility.DEFAULT;
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
    public boolean isFormVisible() {
        return Visibility.formVisible(visibility);
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public FieldType getFieldType() {
        if (fieldType == null) {
            return FieldType.UNKNOWN;
        }
        return fieldType;
    }

    @Override
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
