package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl.EntityInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public final class EntityInfoBuilder {

    private EntityInfoBuilder() throws IllegalAccessException {
        throw new IllegalAccessException("EntityInfoBuilder: Not instance-able object");
    }

    public static EntityInfo build(ClassMetadata classMetadata) {
        RawEntityInfo rawEntityInfo = RawEntityInfoBuilder.buildRawEntityInfo(classMetadata);

        Class entityType = classMetadata.getEntityClz();
        boolean withHierarchy = false;
        if (classMetadata instanceof ClassTreeMetadata) {
            withHierarchy = true;
        }
        Collection<Class> refEntries = rawEntityInfo.getReferencingEntries();
        Map<String, IEntityInfo> childInfoMap = new HashMap<String, IEntityInfo>();
        if(refEntries != null && !refEntries.isEmpty()){
            for (Class entry : refEntries){
                ClassMetadata entryCm = classMetadata.getReferencingClassMetadata(entry);
                RawEntityInfo entryRawEntityInfo = RawEntityInfoBuilder.buildRawEntityInfo(entryCm);
                EntityInfo entryEntityInfo = build(entry, entryRawEntityInfo, false, null);
                IEntityInfo childInfo = new EntityGridInfo(entryEntityInfo);
                childInfoMap.put(entry.getName(), childInfo);
            }
        }
        EntityInfo entityInfo = build(entityType, rawEntityInfo, withHierarchy, childInfoMap);
        return entityInfo;
    }

    private static EntityInfo build(Class entityType, RawEntityInfo rawEntityInfo, boolean withHierarchy, Map<String, IEntityInfo> childInfoMap) {
        Map<String, IFieldInfo> fields = rawEntityInfo.getFields();
        EntityInfoImpl entityInfo = null;
        {//make tabs
            List<ITabInfo> tabTemp = new ArrayList<ITabInfo>();
            for (RawTabInfo rawTabInfo : rawEntityInfo.getTabs()) {
                List<IGroupInfo> groupTemp = new ArrayList<IGroupInfo>();
                for (RawGroupInfo rawGroupInfo : rawTabInfo.getGroups()) {
                    List<String> groupFieldsOrdered = NamedOrderedInfo.NameSorter.makeNamesOrdered(rawGroupInfo.getFields(), fields);
                    IGroupInfo groupInfo = InfoCreator.createGroupFormInfo(rawGroupInfo, groupFieldsOrdered);
                    groupTemp.add(groupInfo);
                }
                ITabInfo tabInfo = InfoCreator.createTabFormInfo(rawTabInfo, groupTemp);
                tabTemp.add(tabInfo);
            }
            List<ITabInfo> tabs = NamedOrderedInfo.NameSorter.makeObjectOrdered(tabTemp);
            entityInfo = new EntityInfoImpl(entityType, withHierarchy, tabs, rawEntityInfo.getFields());
        }

        {// make grid field list
            List<String> orderedGridNameList = NamedOrderedInfo.NameSorter.makeNamesOrdered(rawEntityInfo.getGridFields(), fields);
            entityInfo.setGridFields(orderedGridNameList);
        }

        entityInfo.copyNamedInfo(rawEntityInfo);

        entityInfo.setIdField(rawEntityInfo.getIdField());
        entityInfo.setNameField(rawEntityInfo.getNameField());
        entityInfo.setPrimarySearchField(rawEntityInfo.getPrimarySearchField());
        if(childInfoMap != null){
            for(Map.Entry<String, IEntityInfo> entry : childInfoMap.entrySet()){
                entityInfo.addReferencingEntryInfo(entry.getKey(), entry.getValue());
            }
        }
        return entityInfo;
    }

    private static class InfoCreator {
        static ITabInfo createTabFormInfo(RawTabInfo rawTabInfo, List<IGroupInfo> groups) {
            TabInfoImpl tabFormInfo = new TabInfoImpl(groups);
            tabFormInfo.copyNamedInfo(rawTabInfo);
            return tabFormInfo;
        }

        static IGroupInfo createGroupFormInfo(RawGroupInfo groupInfo, List<String> fields) {
            GroupInfoImpl groupFormInfo = new GroupInfoImpl(fields);
            groupFormInfo.copyNamedInfo(groupInfo);
            return groupFormInfo;
        }
    }
}
