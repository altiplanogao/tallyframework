package com.taoswork.tallybook.descriptor.metadata.fieldmetadata;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.friendly.FriendlyOrderedMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

public final class BasicFieldMetaObject extends FriendlyOrderedMeta implements Serializable {
    private final static Logger LOGGER = LoggerFactory.getLogger(BasicFieldMetaObject.class);
    private final int originalOrder;
    private boolean editable = true;
    protected boolean required = false;
    protected boolean ignored = false;
    protected int length = PersistField.DEFAULT_LENGTH;

    private final String declaringClassName;
    private final Class fieldClass;
    private transient Field field;

    private Class<? extends IFieldGate> fieldValueGateOverride;
    private boolean skipDefaultFieldValueGate = false;

    private String tabName;
    private String groupName;
    private FieldType fieldType;
    private int visibility;

    public BasicFieldMetaObject(int originalOrder, Field field) {
        this.originalOrder = originalOrder;
        name = field.getName();
        declaringClassName = field.getDeclaringClass().getName();
        fieldClass = field.getType();
        this.setTransientField(field);
    }

    private void setTransientField(Field field) {
        this.field = field;
        this.field.setAccessible(true);
    }

    public Field getField() {
        if (this.field == null) {
            try {
                Class ownerClz = Class.forName(declaringClassName);
                Field field = ownerClz.getDeclaredField(this.name);
                this.setTransientField(field);
            } catch (ClassNotFoundException e) {
                LOGGER.error("Field declaring class not found");
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                LOGGER.error("Field not found");
                throw new RuntimeException(e);
            }
        }
        return this.field;
    }


    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldTypeIfUnknown(FieldType fieldType) {
        if (FieldType.UNKNOWN.equals(this.fieldType)) {
            this.fieldType = fieldType;
        }
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isId() {
        return FieldType.ID == fieldType;
    }

    public void setId(boolean id) {
        if (id) {
            fieldType = FieldType.ID;
        } else if (fieldType == FieldType.ID) {
            fieldType = FieldType.UNKNOWN;
        }
    }

    public boolean isNameField() {
        return FieldType.NAME == fieldType;
    }

    public void setNameField(boolean nameField) {
        if (nameField) {
            fieldType = FieldType.NAME;
        } else if (fieldType == FieldType.NAME) {
            fieldType = FieldType.UNKNOWN;
        }
    }

    public String getDeclaringClassName() {
        return declaringClassName;
    }

    public Class getFieldClass() {
        return fieldClass;
    }

    public int getOriginalOrder() {
        return originalOrder;
    }

    public void setFieldValueGate(Class<? extends IFieldGate> fvg, boolean skipDefaultFieldValueGate) {
        this.fieldValueGateOverride = fvg;
        this.skipDefaultFieldValueGate = skipDefaultFieldValueGate;
    }

    public Class<? extends IFieldGate> getFieldValueGateOverride() {
        return fieldValueGateOverride;
    }

    public boolean isSkipDefaultFieldValueGate() {
        return skipDefaultFieldValueGate;
    }

    public boolean getIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
