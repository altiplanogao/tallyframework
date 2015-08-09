package com.taoswork.tallybook.dynamic.datameta.description.infos.main;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfo extends NamedInfo, IEntityInfo {

    String getIdField();

    String getNameField();

    String getPrimarySearchField();

    FieldInfo getField(String fieldName);

    Map<String, FieldInfo> getFields();

    List<ITabInfo> getTabs();

    List<String> getGridFields();
}
