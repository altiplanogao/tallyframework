package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.FieldType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface FieldInfoRW extends NamedOrderedInfoRW, FieldInfo {
    void setVisibility(int visibility);

    void setNameField(boolean nameField);

    void setFieldType(FieldType fieldType);

    void merge(FieldInfo another);

    FieldInfo setSupportSort(boolean supportSort);

    FieldInfo setSupportFilter(boolean supportFilter);
}
