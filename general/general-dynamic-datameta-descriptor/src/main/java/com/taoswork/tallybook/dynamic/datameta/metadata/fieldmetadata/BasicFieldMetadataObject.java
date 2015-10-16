package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.FriendlyOrderedMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

public class BasicFieldMetadataObject extends FriendlyOrderedMetadata implements Serializable {
    private final static Logger LOGGER = LoggerFactory.getLogger(BasicFieldMetadataObject.class);
    private final int originalOrder;
    protected boolean required = false;
    private String declaringClassName;
    private transient Field field;
    private Class fieldClass;
    private String tabName;
    private String groupName;
    private FieldType fieldType;
    private int visibility;

    public BasicFieldMetadataObject(int originalOrder, Field field) {
        setField(field);
        this.originalOrder = originalOrder;
    }

    public Field getField() {
        if (this.field == null) {
            try {
                Class ownerClz = Class.forName(declaringClassName);
                Field field = ownerClz.getDeclaredField(this.name);
                this.setField(field);
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

    public void setField(Field field) {
        this.field = field;
        this.field.setAccessible(true);
        name = field.getName();
        declaringClassName = field.getDeclaringClass().getName();
        fieldClass = field.getType();
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
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

    public Class getFieldClass() {
        return fieldClass;
    }

    public int getOriginalOrder() {
        return originalOrder;
    }

}
