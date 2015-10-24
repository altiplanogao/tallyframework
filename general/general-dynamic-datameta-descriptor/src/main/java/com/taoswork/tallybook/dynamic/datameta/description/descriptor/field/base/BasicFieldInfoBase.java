package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IBasicFieldInfo;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public abstract class BasicFieldInfoBase extends FieldInfoBase implements IBasicFieldInfoRW {
    private boolean supportSort = true;
    private boolean supportFilter = true;
    public boolean nameField = false;

    public BasicFieldInfoBase(String name, String friendlyName) {
        super(name, friendlyName);
    }

    @Override
    public boolean isIdField() {
        return getFieldType().equals(FieldType.ID);
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
    public boolean isSupportSort() {
        return supportSort;
    }

    @Override
    public IBasicFieldInfo setSupportSort(boolean supportSort) {
        this.supportSort = supportSort;
        return this;
    }

    @Override
    public boolean isSupportFilter() {
        return supportFilter;
    }

    @Override
    public IBasicFieldInfo setSupportFilter(boolean supportFilter) {
        this.supportFilter = supportFilter;
        return this;
    }

    @Override
    public boolean isGridVisible() {
        return Visibility.gridVisible(visibility);
    }

    @Override
    public boolean isCollection() {
        return false;
    }
}
