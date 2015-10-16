package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.impl.GroupInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.impl.TabInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl.EntityInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public final class EntityInfoBuilder {

    private EntityInfoBuilder() throws IllegalAccessException {
        throw new IllegalAccessException("EntityInfoBuilder: Not instance-able object");
    }

    public static EntityInfo build(ClassMetadata classMetadata) {
        RawEntityInsight rawEntityInsight = EntityInsightBuilder.buildEntityInsight(classMetadata);

        Class entityType = classMetadata.getEntityClz();
        boolean withHierarchy = false;
        if (classMetadata instanceof ClassTreeMetadata) {
            withHierarchy = true;
        }
        return build(entityType, rawEntityInsight, withHierarchy);
    }

    private static EntityInfo build(Class entityType, RawEntityInsight rawEntityInsight, boolean withHierarchy) {
        Map<String, FieldInfo> fields = rawEntityInsight.getFields();
        EntityInfoImpl entityInfo = null;
        {//make tabs
            List<ITabInfo> tabTemp = new ArrayList<ITabInfo>();
            for (RawTabInsight rawTabInsight : rawEntityInsight.getTabs()) {
                List<IGroupInfo> groupTemp = new ArrayList<IGroupInfo>();
                for (RawGroupInsight rawGroupInsight : rawTabInsight.getGroups()) {
                    List<String> groupFieldsOrdered = NamedOrderedInfo.NameSorter.makeNamesOrdered(rawGroupInsight.getFields(), fields);
                    IGroupInfo groupInfo = InfoCreator.createGroupFormInfo(rawGroupInsight, groupFieldsOrdered);
                    groupTemp.add(groupInfo);
                }
                ITabInfo tabInfo = InfoCreator.createTabFormInfo(rawTabInsight, groupTemp);
                tabTemp.add(tabInfo);
            }
            List<ITabInfo> tabs = NamedOrderedInfo.NameSorter.makeObjectOrdered(tabTemp);
            entityInfo = new EntityInfoImpl(entityType, withHierarchy, tabs, rawEntityInsight.getFields());
        }

        {// make grid field list
            List<String> orderedGridNameList = NamedOrderedInfo.NameSorter.makeNamesOrdered(rawEntityInsight.getGridFields(), fields);
            entityInfo.setGridFields(orderedGridNameList);
        }

        entityInfo.copyNamedInfo(rawEntityInsight);

        entityInfo.setIdField(rawEntityInsight.getIdField());
        entityInfo.setNameField(rawEntityInsight.getNameField());
        entityInfo.setPrimarySearchField(rawEntityInsight.getPrimarySearchField());
        return entityInfo;
    }

    private static class InfoCreator {
        static ITabInfo createTabFormInfo(RawTabInsight rawTabInsight, List<IGroupInfo> groups) {
            TabInfoImpl tabFormInfo = new TabInfoImpl(groups);
            tabFormInfo.copyNamedInfo(rawTabInsight);
            return tabFormInfo;
        }

        static IGroupInfo createGroupFormInfo(RawGroupInsight groupInfo, List<String> fields) {
            GroupInfoImpl groupFormInfo = new GroupInfoImpl(fields);
            groupFormInfo.copyNamedInfo(groupInfo);
            return groupFormInfo;
        }
    }
}
