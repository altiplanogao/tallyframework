package com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInsight;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInsightRW;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityInsightRW extends NamedInfoRW, EntityInsight {
    void addField(FieldInfoRW fieldInfo);

    FieldInfoRW getFieldRW(String fieldName);

    void addTab(TabInsightRW tabInfo);

    TabInsightRW getTabRW(String tabName);

    void addGridField(String fieldName);

    EntityInsightRW setIdField(String idField);

    EntityInsightRW setNameField(String nameField);

    void finishWriting();

}
