package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendlyOrdered;
import com.taoswork.tallybook.dynamic.datameta.metadata.property.Property;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IFieldValueGate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.lang.reflect.Field;

public interface IFieldMetadata extends IFriendlyOrdered {
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

    Property[] getProperties(Object obj) throws IllegalAccessException;

    Class<? extends IFieldValueGate> getFieldValueGateOverride();

    boolean getSkipDefaultFieldValueGate();
}
