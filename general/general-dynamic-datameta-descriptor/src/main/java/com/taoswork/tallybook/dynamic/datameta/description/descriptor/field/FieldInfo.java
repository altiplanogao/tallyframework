package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.FieldType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;

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

    FieldType getFieldType();

    boolean isSupportSort();

    boolean isSupportFilter();
}
