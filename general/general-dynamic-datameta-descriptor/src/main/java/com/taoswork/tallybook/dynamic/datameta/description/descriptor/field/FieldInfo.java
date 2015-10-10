package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface FieldInfo extends NamedOrderedInfo {
    int getVisibility();

    boolean isGridVisible();

    boolean isFormVisible();

    boolean isCollection();

    boolean isNameField();

    boolean isIdField();

    boolean isRequired();

    FieldType getFieldType();

    boolean isSupportSort();

    boolean isSupportFilter();

    Map<FieldFacetType, IFieldFacet> getFacets();

    IFieldFacet getFacet(FieldFacetType facetType);
}
