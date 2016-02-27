package com.taoswork.tallybook.descriptor.description.infos.main.impl;

import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfoRW extends EntityInfo, NamedInfoRW {
    void setIdField(String idField);

    void setNameField(String nameField);

    void setPrimarySearchField(String primarySearchField);

    void setFields(Map<String, IFieldInfo> fields);

    void setTabs(List<ITabInfo> tabs);

    void setGridFields(List<String> gridFields);

    void addReferencingInfo(String entityName, EntityInfo info);
}
