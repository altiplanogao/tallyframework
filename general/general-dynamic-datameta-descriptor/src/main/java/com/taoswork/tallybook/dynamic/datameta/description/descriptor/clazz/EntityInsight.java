package com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInsight;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityInsight
    extends NamedInfo, Serializable {

    FieldInfo getField(String fieldName);

    Map<String, FieldInfo> getFields();


    TabInsight getTab(String tabName);

    Collection<? extends TabInsight> getTabs();


    Collection<String> getGridFields();

    String getIdField();

    String getNameField();

    String getPrimarySearchField();
}
