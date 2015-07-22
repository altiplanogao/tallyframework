package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface FieldInfo extends NamedInfo {
    int getVisibility();

    boolean isGridVisible();

    boolean isCollection();

    boolean isNameField();

    boolean isIdField();

    SupportedFieldType getFieldType();

    boolean isSupportSort();

    boolean isSupportFilter();
}
