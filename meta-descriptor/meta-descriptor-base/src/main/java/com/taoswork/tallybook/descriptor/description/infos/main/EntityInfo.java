package com.taoswork.tallybook.descriptor.description.infos.main;

import com.taoswork.tallybook.descriptor.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfo extends NamedInfo, IEntityInfo {

    String getPrimarySearchField();

    IFieldInfo getField(String fieldName);

    Map<String, IFieldInfo> getFields();

    List<ITabInfo> getTabs();

    List<String> getGridFields();

    Map<String, EntityInfo> getReferencingInfos();

    Map<String, IEntityInfo> getReferencingInfosAsType(EntityInfoType entityInfoType);
}
