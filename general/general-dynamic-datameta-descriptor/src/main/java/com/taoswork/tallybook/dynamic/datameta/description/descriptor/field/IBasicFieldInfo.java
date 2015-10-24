package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public interface IBasicFieldInfo extends IFieldInfo {

    boolean isIdField();

    boolean isNameField();

    boolean isSupportSort();

    boolean isSupportFilter();

    boolean isGridVisible();
}
