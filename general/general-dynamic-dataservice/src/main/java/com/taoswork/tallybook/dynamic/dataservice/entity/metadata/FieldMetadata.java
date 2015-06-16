package com.taoswork.tallybook.dynamic.dataservice.entity.metadata;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet.FieldFacetType;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet.IFieldFacet;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/24.
 */
public class FieldMetadata extends FriendlyMetadata implements Serializable {
    public Field field;

    public String tabName;
    public String groupName;
    public int visibility;

    public SupportedFieldType fieldType;

    public final Map<FieldFacetType, IFieldFacet> facets = new HashMap<FieldFacetType, IFieldFacet>();

    public FieldMetadata(Field field){
        setField(field);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public boolean isId() {
        return SupportedFieldType.ID == fieldType;
    }

    public void setId(boolean id) {
        if(id){
            fieldType = SupportedFieldType.ID;
        } else if(fieldType == SupportedFieldType.ID){
            fieldType = SupportedFieldType.UNKNOWN;
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

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isNameField() {
        return SupportedFieldType.NAME == fieldType;
    }

    public void setNameField(boolean nameField) {
        if(nameField){
            fieldType = SupportedFieldType.NAME;
        } else if(fieldType == SupportedFieldType.NAME){
            fieldType = SupportedFieldType.UNKNOWN;
        }
    }

    public SupportedFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(SupportedFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isCollectionField(){
        return facets.containsKey(FieldFacetType.Collection);
    }

    public void addFacet(IFieldFacet facet){
        IFieldFacet existingFacet = facets.getOrDefault(facet.getType(), null);
        if(existingFacet == null){
            facets.put(facet.getType(), facet);
        }else {
            existingFacet.merge(facet);
        }
    }

    public boolean showOnGrid() {
        return (!isCollectionField()) && Visibility.gridVisible(visibility);
    }

    @Override
    public String toString() {
        return "Field Meta{'" +
                field.getName() + "'@" + field.getDeclaringClass().getSimpleName() +
                " " + Visibility.makeString(visibility) +
                '}';
    }
}
