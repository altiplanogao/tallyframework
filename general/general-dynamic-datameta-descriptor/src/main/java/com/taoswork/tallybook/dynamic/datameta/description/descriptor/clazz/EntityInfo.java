package com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityInfo extends IEntityInfo {
    FieldInfo getField(String fieldName);

    Collection<? extends FieldInfo> getFields();

    TabInfo getTab(String tabName);

    Collection<? extends TabInfo> getTabs();

    Collection<String> getGridFields();

    String getIdField();

    String getPrimarySearchField();
}
