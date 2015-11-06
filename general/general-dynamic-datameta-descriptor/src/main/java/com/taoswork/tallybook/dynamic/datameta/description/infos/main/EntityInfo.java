package com.taoswork.tallybook.dynamic.datameta.description.infos.main;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.ITabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfo extends NamedInfo, IEntityInfo {

    String getIdField();

    String getNameField();

    String getPrimarySearchField();

    IFieldInfo getField(String fieldName);

    Map<String, IFieldInfo> getFields();

    List<ITabInfo> getTabs();

    List<String> getGridFields();

    Map<String, EntityInfo> getReferencingInfos();

    Map<String, IEntityInfo> getReferencingInfosAsType(EntityInfoType entityInfoType);
}
