package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class FieldInfoImpl
    extends NamedOrderedInfoImpl
    implements FieldInfoRW {

    public int visibility = Visibility.DEFAULT;
    public boolean nameField = false;
    public boolean required = false;
    private boolean isCollection;
    private boolean supportSort = true;
    private boolean supportFilter = true;

    private FieldType fieldType = FieldType.UNKNOWN;

    private Map<FieldFacetType, IFieldFacet> facets;

    public FieldInfoImpl() {

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
    public boolean isFormVisible() {
        return Visibility.formVisible(visibility);
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

    @Override
    public boolean isIdField() {
        return getFieldType().equals(FieldType.ID);
    }

    @Override
    public void merge(FieldInfo another) {
        if (this.equals(another)) {
            return;
        }
        //TODO
    }

    @Override
    public boolean isSupportSort() {
        return supportSort;
    }

    @Override
    public FieldInfo setSupportSort(boolean supportSort) {
        this.supportSort = supportSort;
        return this;
    }

    @Override
    public boolean isSupportFilter() {
        return supportFilter;
    }

    @Override
    public FieldInfo setSupportFilter(boolean supportFilter) {
        this.supportFilter = supportFilter;
        return this;
    }

    @Override
    public Map<FieldFacetType, IFieldFacet> getFacets() {
        if (facets != null) {
            return Collections.unmodifiableMap(facets);
        }
        return null;
    }

    @Override
    public IFieldFacet getFacet(FieldFacetType facetType) {
        if (facets != null) {
            return facets.getOrDefault(facetType, null);
        }
        return null;
    }

    @Override
    public void addFacet(IFieldFacet facetInfo) {
        if (facetInfo == null)
            return;
        if (facets == null) {
            facets = new HashMap<FieldFacetType, IFieldFacet>();
        }
        facets.put(facetInfo.getType(), facetInfo);
    }
}
