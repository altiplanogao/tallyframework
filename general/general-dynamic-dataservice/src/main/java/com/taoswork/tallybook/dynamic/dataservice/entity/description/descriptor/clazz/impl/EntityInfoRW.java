package com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.tab.impl.TabInfoRW;

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
