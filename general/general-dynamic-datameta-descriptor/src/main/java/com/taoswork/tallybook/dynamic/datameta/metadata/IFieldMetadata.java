package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendlyOrdered;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IFieldValueGate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.io.Serializable;
import java.lang.reflect.Field;

public interface IFieldMetadata extends IFriendlyOrdered, Serializable {
    String getTabName();

    String getGroupName();

    FieldType getFieldType();

    boolean isEditable();

    boolean isRequired();

    int getVisibility();

    boolean isId();

    boolean isNameField();

    void setNameField(boolean b);

    boolean isPrimitiveField();

    boolean isCollectionField();

    Field getField();

    boolean showOnGrid();

    Class getFieldClass();

    Class<? extends IFieldValueGate> getFieldValueGateOverride();

    boolean getSkipDefaultFieldValueGate();

    boolean getIgnored();
}
