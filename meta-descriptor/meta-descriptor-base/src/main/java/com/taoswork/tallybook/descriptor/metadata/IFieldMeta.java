package com.taoswork.tallybook.descriptor.metadata;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.friendly.IFriendlyOrdered;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

public interface IFieldMeta extends IFriendlyOrdered, Serializable {
    String getTabName();

    String getGroupName();

    FieldType getFieldType();

    boolean isEditable();

    boolean isRequired();

    int getVisibility();

    boolean isId();

    boolean isNameField();

    void setNameField(boolean b);
//
//    boolean isPrimitiveField();

    boolean isCollectionField();

    Field getField();

    boolean showOnGrid();

    Class getFieldClass();

    Class<? extends IFieldGate> getFieldValueGateOverride();

    boolean getSkipDefaultFieldValueGate();

    boolean getIgnored();

    int gatherReferencingTypes(Collection<Class> collection);
}
