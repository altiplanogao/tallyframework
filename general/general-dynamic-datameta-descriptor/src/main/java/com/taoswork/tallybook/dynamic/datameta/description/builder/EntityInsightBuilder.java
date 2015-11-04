package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IBasicFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.GroupMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.TabMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendly;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendlyOrdered;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
final class EntityInsightBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInsightBuilder.class);

    private EntityInsightBuilder() throws IllegalAccessException {
        throw new IllegalAccessException("EntityInsightBuilder: Not instance-able object");
    }

    /**
     * Convert ClassMetadata object to RawEntityInsight object,
     * It is the first step of entity information sorting.
     *
     * @param classMetadata
     * @return
     */
    public static RawEntityInsight buildEntityInsight(ClassMetadata classMetadata) {
        final RawEntityInsightRW entityInsight = InfoCreator.createEntityInsight(classMetadata);
        entityInsightAppendMetadata(entityInsight, classMetadata);
        return entityInsight;
    }

    private static void entityInsightAppendMetadata(RawEntityInsightRW entityInsight, final ClassMetadata classMetadata) {

        final ClassMetadata topClassMetadata = classMetadata;
        //add tabs
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        for (Map.Entry<String, TabMetadata> tabMetadataEntry : tabMetadataMap.entrySet()) {
            TabMetadata tabMetadata = tabMetadataEntry.getValue();
            RawTabInsightRW tabInsight = InfoCreator.createTabInsight(tabMetadata);
            entityInsight.addTab(tabInsight);
        }

        //add fields
        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            RawGroupInsightRW groupInsight = null;
            {
                //handle groups
                String tabName = fieldMetadata.getTabName();
                String groupName = fieldMetadata.getGroupName();

                RawTabInsightRW tabInsight = entityInsight.getTabRW(tabName);
                groupInsight = tabInsight.getGroupRW(groupName);
                if (groupInsight == null) {
                    groupInsight = InfoCreator.createGroupInsight(classMetadata.getReadonlyGroupMetadataMap().get(groupName));
                    tabInsight.addGroup(groupInsight);
                }
            }

            Collection<IFieldInfo> fieldInfos = FieldInfoBuilder.createFieldInfos(topClassMetadata, fieldMetadata);
            for(IFieldInfo fi : fieldInfos){
                entityInsight.addField(fi);
                String fieldName = fi.getName();
                if(fi instanceof IBasicFieldInfo){
                    IBasicFieldInfo bfi = (IBasicFieldInfo) fi;
                    if(bfi.isGridVisible()){
                        entityInsight.addGridField(fieldName);
                    }
                }
                groupInsight.addField(fieldName);
            }
        }
        String idFieldName = classMetadata.getIdFieldName();
        if(StringUtils.isNotEmpty(idFieldName)){
            entityInsight.addGridField(idFieldName);
            entityInsight.setIdField(idFieldName);
        }
        entityInsight.setNameField(classMetadata.getNameFieldName());
        entityInsight.finishWriting();
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

        static void copyOrderedFriendlyMetadata(IFriendlyOrdered source, NamedOrderedInfoRW target) {
            copyFriendlyMetadata(source, target);
            target.setOrder(source.getOrder());
        }

        static void copyFriendlyMetadata(IFriendly source, NamedInfoRW target) {
            target.setFriendlyName(source.getFriendlyName())
                .setName(source.getName());
        }
    }
}
