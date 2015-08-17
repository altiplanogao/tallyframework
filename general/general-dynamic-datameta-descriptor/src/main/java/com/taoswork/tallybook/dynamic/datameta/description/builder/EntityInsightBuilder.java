package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInsight;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl.EntityInsightImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.impl.EntityInsightRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl.GroupInsightImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl.GroupInsightRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInsightImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl.TabInsightRW;
import com.taoswork.tallybook.dynamic.datameta.metadata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public final class EntityInsightBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInsightBuilder.class);

    private EntityInsightBuilder() throws IllegalAccessException{
        throw new IllegalAccessException("EntityInsightBuilder: Not instance-able object");
    }

    private static class InfoCreator {
        static EntityInsightRW createEntityInsight(ClassMetadata classMetadata) {
            EntityInsightRW entityInsight = new EntityInsightImpl();
            copyFriendlyMetadata(classMetadata, entityInsight);
            return entityInsight;
        }

        static GroupInsightRW createGroupInsight(GroupMetadata groupMetadata) {
            GroupInsightRW groupInsight = new GroupInsightImpl();
            copyOrderedFriendlyMetadata(groupMetadata, groupInsight);
            return groupInsight;
        }

        static TabInsightRW createTabInsight(TabMetadata tabMetadata) {
            TabInsightRW tabInsight = new TabInsightImpl();
            copyOrderedFriendlyMetadata(tabMetadata, tabInsight);
            return tabInsight;
        }

        static FieldInfoRW createFieldInfo(FieldMetadata fieldMetadata) {
            FieldInfoRW fieldInfo = new FieldInfoImpl();
            copyOrderedFriendlyMetadata(fieldMetadata, fieldInfo);

            fieldInfo.setVisibility(fieldMetadata.getVisibility());
            fieldInfo.setNameField(fieldMetadata.isNameField());
            fieldInfo.setFieldType(fieldMetadata.getFieldType());
            return fieldInfo;
        }

        static void copyOrderedFriendlyMetadata(FriendlyMetadata source, NamedOrderedInfoRW target) {
            copyFriendlyMetadata(source, target);
            target.setOrder(source.getOrder());
        }

        static void copyFriendlyMetadata(FriendlyMetadata source, NamedInfoRW target) {
            target.setFriendlyName(source.getFriendlyName())
                .setName(source.getName());
        }
    }

    public static EntityInsight buildEntityInsight(ClassMetadata classMetadata) {
        final EntityInsightRW entityInsight = InfoCreator.createEntityInsight(classMetadata);
        entityInsightAppendMetadata(entityInsight, classMetadata);
        return entityInsight;
    }

    private static void entityInsightAppendMetadata(EntityInsightRW entityInsight, ClassMetadata classMetadata) {

        //add tabs
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        for (Map.Entry<String, TabMetadata> tabMetadataEntry : tabMetadataMap.entrySet()) {
            TabMetadata tabMetadata = tabMetadataEntry.getValue();
            TabInsightRW tabInsight = InfoCreator.createTabInsight(tabMetadata);
            entityInsight.addTab(tabInsight);
        }

        //add fields
        Map<String, FieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, FieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()){
            FieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            FieldInfoRW fieldInfo = InfoCreator.createFieldInfo(fieldMetadata);
            entityInsight.addField(fieldInfo);

            String fieldName = fieldInfo.getName();

            if (fieldMetadata.showOnGrid() || fieldMetadata.isId()) {
                entityInsight.addGridField(fieldName);
            }

            if(fieldMetadata.isId()){
                entityInsight.setIdField(fieldName);
            }

            {
                //handle groups
                String tabName = fieldMetadata.getTabName();
                String groupName = fieldMetadata.getGroupName();

                TabInsightRW tabInsight = entityInsight.getTabRW(tabName);
                GroupInsightRW groupInsight = tabInsight.getGroupRW(groupName);
                if (groupInsight == null) {
                    groupInsight = InfoCreator.createGroupInsight(classMetadata.getReadonlyGroupMetadataMap().get(groupName));
                    tabInsight.addGroup(groupInsight);
                }
                groupInsight.addField(fieldInfo.getName());
            }
        }

        entityInsight.finishWriting();

    }
}
