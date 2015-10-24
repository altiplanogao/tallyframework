package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.basic;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface IFieldInfoRW extends NamedOrderedInfoRW, IFieldInfo {
    void setVisibility(int visibility);

    void setRequired(boolean required);

    void setNameField(boolean nameField);

    void setFieldType(FieldType fieldType);

    void merge(IFieldInfo another);

    IFieldInfo setSupportSort(boolean supportSort);

    IFieldInfo setSupportFilter(boolean supportFilter);

    void addFacet(IFieldFacet enumFacetInfo);
}
