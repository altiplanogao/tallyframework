package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawEntityInsight
    extends NamedInfo, Serializable {

    FieldInfo getField(String fieldName);

    Map<String, FieldInfo> getFields();


    RawTabInsight getTab(String tabName);

    Collection<? extends RawTabInsight> getTabs();


    Collection<String> getGridFields();

    String getIdField();

    String getNameField();

    String getPrimarySearchField();
}
