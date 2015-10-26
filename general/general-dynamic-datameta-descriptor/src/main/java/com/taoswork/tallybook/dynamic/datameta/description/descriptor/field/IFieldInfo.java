package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface IFieldInfo extends NamedOrderedInfo {
    int getVisibility();

    boolean isRequired();

    FieldType getFieldType();

    boolean isFormVisible();

    boolean isCollection();
}