package com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfoRW extends EntityInfo, NamedInfoRW {
    void setIdField(String idField);

    void setNameField(String nameField);

    void setPrimarySearchField(String primarySearchField);

    void setFields(Map<String, FieldInfo> fields);

    void setTabs(List<ITabInfo> tabs);

    void setGridFields(List<String> gridFields);
}
