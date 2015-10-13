package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetaFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldMetadata extends FriendlyMetadata implements Serializable {
    private final static Logger LOGGER = LoggerFactory.getLogger(FieldMetadata.class);

    private String declaringClassName;

    private transient Field field;

    private String tabName;
    private String groupName;
    protected boolean required  =false;
    private int visibility;

    private FieldType fieldType;
    private Class fieldClass;

    private final int originalOrder;

    public final Map<FieldFacetType, IFieldMetaFacet> facets = new HashMap<FieldFacetType, IFieldMetaFacet>();

    public FieldMetadata(int originalOrder, Field field){
        setField(field);
        this.originalOrder = originalOrder;
    }

    public void setField(Field field) {
        this.field = field;
        this.field.setAccessible(true);
        name = field.getName();
        declaringClassName = field.getDeclaringClass().getName();
    }

    public Field getField() {
        if(this.field == null){
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

    public boolean isId() {
        return FieldType.ID == fieldType;
    }

    public void setId(boolean id) {
        if(id){
            fieldType = FieldType.ID;
        } else if(fieldType == FieldType.ID){
            fieldType = FieldType.UNKNOWN;
        }
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

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Class getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(Class fieldClass) {
        this.fieldClass = fieldClass;
    }

    public boolean isCollectionField(){
        return facets.containsKey(FieldFacetType.Collection);
    }

    public void addFacet(IFieldMetaFacet facet){
        IFieldMetaFacet existingFacet = facets.getOrDefault(facet.getType(), null);
        if(existingFacet == null){
            facets.put(facet.getType(), facet);
        }else {
            existingFacet.merge(facet);
        }
    }

    public IFieldMetaFacet getFacet(FieldFacetType facetType){
        IFieldMetaFacet existingFacet = facets.getOrDefault(facetType, null);
        return existingFacet;
    }

    public boolean showOnGrid() {
        return (!isCollectionField()) && Visibility.gridVisible(visibility);
    }

    @Override
    public String toString() {
        return "Field Meta{'" +
                name + "'@" + declaringClassName +
                " " + Visibility.makeString(visibility) +
                '}';
    }

    public int getOriginalOrder() {
        return originalOrder;
    }

    @Override
    public FriendlyMetadata setOrder(int order) {
        return super.setOrder(order);
    }
}
