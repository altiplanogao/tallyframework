package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInsight;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInsight;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInsight;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.impl.GroupInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.impl.TabInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl.EntityInfoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public final class EntityInfoBuilder {

    private EntityInfoBuilder() throws IllegalAccessException{
        throw new IllegalAccessException("EntityInfoBuilder: Not instance-able object");
    }

    public static EntityInfo build(EntityInsight entityInsight) {
        Map<String, FieldInfo> fields = entityInsight.getFields();
        EntityInfoImpl entityInfo = null;
        {//make tabs
            List<ITabInfo> tabTemp = new ArrayList<ITabInfo>();
            for (TabInsight tabInsight : entityInsight.getTabs()) {
                List<IGroupInfo> groupTemp = new ArrayList<IGroupInfo>();
                for (GroupInsight groupInsight : tabInsight.getGroups()) {
                    List<String> groupFieldsOrdered = NamedOrderedInfo.NameSorter.makeNamesOrdered(groupInsight.getFields(), fields);
                    IGroupInfo groupInfo = InfoCreator.createGroupFormInfo(groupInsight, groupFieldsOrdered);
                    groupTemp.add(groupInfo);
                }
                List<IGroupInfo> groups = NamedOrderedInfo.NameSorter.makeObjectOrdered(groupTemp);
                ITabInfo tabInfo = InfoCreator.createTabFormInfo(tabInsight, groups);
                tabTemp.add(tabInfo);
            }
            List<ITabInfo> tabs = NamedOrderedInfo.NameSorter.makeObjectOrdered(tabTemp);
            entityInfo = new EntityInfoImpl(tabs, entityInsight.getFields());
        }

        {// make grid field list
            List<String> orderedGridNameList = NamedOrderedInfo.NameSorter.makeNamesOrdered(entityInsight.getGridFields(), fields);
            entityInfo.setGridFields(orderedGridNameList);
        }

        entityInfo.copyNamedInfo(entityInsight);

        entityInfo.setIdField(entityInsight.getIdField());
        entityInfo.setNameField(entityInsight.getNameField());
        entityInfo.setPrimarySearchField(entityInsight.getPrimarySearchField());
        return entityInfo;
    }

    private static class InfoCreator {
        static ITabInfo createTabFormInfo(TabInsight tabInsight, List<IGroupInfo> groups) {
            TabInfoImpl tabFormInfo = new TabInfoImpl(groups);
            tabFormInfo.copyNamedInfo(tabInsight);
            return tabFormInfo;
        }

        static IGroupInfo createGroupFormInfo(GroupInsight groupInfo, List<String> fields) {
            GroupInfoImpl groupFormInfo = new GroupInfoImpl(fields);
            groupFormInfo.copyNamedInfo(groupInfo);
            return groupFormInfo;
        }
    }
}
