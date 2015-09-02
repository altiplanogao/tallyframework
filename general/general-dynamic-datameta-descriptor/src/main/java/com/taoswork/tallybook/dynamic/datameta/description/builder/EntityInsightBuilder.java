package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.EnumFacetInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.metadata.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.EnumFieldMetaFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
final class EntityInsightBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInsightBuilder.class);

    private EntityInsightBuilder() throws IllegalAccessException{
        throw new IllegalAccessException("EntityInsightBuilder: Not instance-able object");
    }

    private static class InfoCreator {
        static RawEntityInsightRW createEntityInsight(ClassMetadata classMetadata) {
            RawEntityInsightRW entityInsight = new RawEntityInsightImpl();
            copyFriendlyMetadata(classMetadata, entityInsight);
            return entityInsight;
        }

        static RawGroupInsightRW createGroupInsight(GroupMetadata groupMetadata) {
            RawGroupInsightRW groupInsight = new RawGroupInsightImpl();
            copyOrderedFriendlyMetadata(groupMetadata, groupInsight);
            return groupInsight;
        }

        static RawTabInsightRW createTabInsight(TabMetadata tabMetadata) {
            RawTabInsightRW tabInsight = new RawTabInsightImpl();
            copyOrderedFriendlyMetadata(tabMetadata, tabInsight);
            return tabInsight;
        }

        static FieldInfoRW createFieldInfo(FieldMetadata fieldMetadata) {
            FieldInfoRW fieldInfo = new FieldInfoImpl();
            copyOrderedFriendlyMetadata(fieldMetadata, fieldInfo);

            fieldInfo.setVisibility(fieldMetadata.getVisibility());
            fieldInfo.setNameField(fieldMetadata.isNameField());
            fieldInfo.setFieldType(fieldMetadata.getFieldType());

            EnumFieldMetaFacet enumFieldFacet = (EnumFieldMetaFacet)fieldMetadata.facets.getOrDefault(FieldFacetType.Enum, null);
            if(enumFieldFacet != null){
                IFieldFacet enumFacetInfo = new EnumFacetInfo(enumFieldFacet.getEnumerationType());
                fieldInfo.addFacet(enumFacetInfo);
            }

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

    public static RawEntityInsight buildEntityInsight(ClassMetadata classMetadata) {
        final RawEntityInsightRW entityInsight = InfoCreator.createEntityInsight(classMetadata);
        entityInsightAppendMetadata(entityInsight, classMetadata);
        return entityInsight;
    }

    private static void entityInsightAppendMetadata(RawEntityInsightRW entityInsight, ClassMetadata classMetadata) {

        //add tabs
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        for (Map.Entry<String, TabMetadata> tabMetadataEntry : tabMetadataMap.entrySet()) {
            TabMetadata tabMetadata = tabMetadataEntry.getValue();
            RawTabInsightRW tabInsight = InfoCreator.createTabInsight(tabMetadata);
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

                RawTabInsightRW tabInsight = entityInsight.getTabRW(tabName);
                RawGroupInsightRW groupInsight = tabInsight.getGroupRW(groupName);
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
