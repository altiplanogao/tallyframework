package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl.EntityInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl.EntityInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl.GroupInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl.GroupInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInfoRW;
import com.taoswork.tallybook.dynamic.datameta.metadata.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityInfoBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInfoBuilder.class);

    private static class InfoCreator {
        static EntityInfoRW createClassInfo(ClassMetadata classMetadata) {
            EntityInfoRW classInfo = new EntityInfoImpl();
            copyFriendlyMetadata(classMetadata, classInfo);
            return classInfo;
        }

        static GroupInfoRW createGroupInfo(GroupMetadata groupMetadata) {
            GroupInfoRW groupInfo = new GroupInfoImpl();
            copyFriendlyMetadata(groupMetadata, groupInfo);
            return groupInfo;
        }

        static TabInfoRW createTabInfo(TabMetadata tabMetadata) {
            TabInfoRW tabInfo = new TabInfoImpl();
            copyFriendlyMetadata(tabMetadata, tabInfo);
            return tabInfo;
        }

        static FieldInfoRW createFieldInfo(FieldMetadata fieldMetadata) {
            FieldInfoRW fieldInfo = new FieldInfoImpl();
            copyFriendlyMetadata(fieldMetadata, fieldInfo);

            fieldInfo.setVisibility(fieldMetadata.getVisibility());
            fieldInfo.setNameField(fieldMetadata.isNameField());
            fieldInfo.setFieldType(fieldMetadata.getFieldType());
            return fieldInfo;
        }

        static void copyFriendlyMetadata(FriendlyMetadata source, NamedInfoRW target) {
            target.setFriendlyName(source.getFriendlyName())
                    .setName(source.getName())
                    .setOrder(source.getOrder());
        }
    }

    public static EntityInfo buildClassInfo(ClassMetadata classMetadata, EntityClassTree polymorphicEntityClzTree, final MetadataService metadataService) {
        final EntityInfoRW classInfo = InfoCreator.createClassInfo(classMetadata);
        classInfoAppendMetadata(classInfo, classMetadata);
        if (polymorphicEntityClzTree != null) {
            polymorphicEntityClzTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
                @Override
                public Void callback(EntityClass parameter) throws AutoTreeException {
                    ClassMetadata childClassMetadata = metadataService.getClassMetadata(parameter.clz.getName());
                    classInfoAppendMetadata(classInfo, childClassMetadata);
                    return null;
                }
            }, true);
        }
        //polymorphicEntityClzTree.
        return classInfo;
    }

    private static void classInfoAppendMetadata(EntityInfoRW classInfo, ClassMetadata classMetadata) {

        //add tabs
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        for (Map.Entry<String, TabMetadata> tabMetadataEntry : tabMetadataMap.entrySet()) {
            TabMetadata tabMetadata = tabMetadataEntry.getValue();
            TabInfoRW tabInfo = InfoCreator.createTabInfo(tabMetadata);
            classInfo.addTab(tabInfo);
        }

//        Map<String, GroupMetadata> groupMetadataMap = classMetadata.getReadonlyGroupMetadataMap();
//        for (Map.Entry<String, GroupMetadata> entry : groupMetadataMap.entrySet()){
//            if(intermediate.groupMetadataMapMerged.containsKey(entry.getKey())){
//                LOGGER.warn("Group with name '{}' already exist.", entry.getKey());
//            }
//            intermediate.groupMetadataMapMerged.put(entry.getKey(), entry.getValue());
//        }
        //add fields
        Map<String, FieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, FieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()){
            FieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            FieldInfoRW fieldInfo = InfoCreator.createFieldInfo(fieldMetadata);
            classInfo.addField(fieldInfo);

            String fieldName = fieldInfo.getName();

            if (fieldMetadata.showOnGrid() || fieldMetadata.isId()) {
                classInfo.addGridField(fieldName);
            }

            if(fieldMetadata.isId()){
                classInfo.setIdField(fieldName);
            }

            {
                //handle groups
                String tabName = fieldMetadata.getTabName();
                String groupName = fieldMetadata.getGroupName();

                TabInfoRW tabInfo = classInfo.getTabRW(tabName);
                GroupInfoRW groupInfo = tabInfo.getGroupRW(groupName);
                if (groupInfo == null) {
                    groupInfo = InfoCreator.createGroupInfo(classMetadata.getReadonlyGroupMetadataMap().get(groupName));
                    tabInfo.addGroup(groupInfo);
                }
                groupInfo.addField(fieldInfo);
            }
        }

        classInfo.finishWriting();

    }
}
