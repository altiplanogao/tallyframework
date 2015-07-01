package com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInfoRW;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityInfoRW extends NamedInfoRW, EntityInfo {
    void addField(FieldInfoRW fieldInfo);

    FieldInfoRW getFieldRW(String fieldName);

    void addTab(TabInfoRW tabInfo);

    TabInfoRW getTabRW(String tabName);

    void addGridField(String fieldName);

    EntityInfoImpl setIdField(String idField);

    void finishWriting();

}
