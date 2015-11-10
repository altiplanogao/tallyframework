package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IBasicFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.GroupMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.TabMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendly;
import com.taoswork.tallybook.dynamic.datameta.metadata.friendly.IFriendlyOrdered;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
final class RawEntityInfoBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(RawEntityInfoBuilder.class);

    private RawEntityInfoBuilder() throws IllegalAccessException {
        throw new IllegalAccessException("RawEntityInfoBuilder: Not instance-able object");
    }

    /**
     * Convert ClassMetadata object to RawEntityInfo object,
     * It is the first step of entity information sorting.
     *
     * @param classMetadata
     * @return
     */
    public static RawEntityInfo buildRawEntityInfo(IClassMetadata classMetadata) {
        final RawEntityInfo rawEntityInfo = RawInfoCreator.createRawEntityInfo(classMetadata);
        rawEntityInfoAppendMetadata(rawEntityInfo, classMetadata);
        return rawEntityInfo;
    }

    private static void rawEntityInfoAppendMetadata(RawEntityInfo rawEntityInfo, final IClassMetadata classMetadata) {
        Collection<Class> collectionTypeReferenced = new HashSet<Class>();

        final IClassMetadata topClassMetadata = classMetadata;
        //add tabs
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        for (Map.Entry<String, TabMetadata> tabMetadataEntry : tabMetadataMap.entrySet()) {
            TabMetadata tabMetadata = tabMetadataEntry.getValue();
            RawTabInfo rawTabInfo = RawInfoCreator.createRawTabInfo(tabMetadata);
            rawEntityInfo.addTab(rawTabInfo);
        }

        //add fields
        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            if(fieldMetadata.getIgnored())
                continue;
            RawGroupInfo rawGroupInfo = null;
            {
                //handle groups
                String tabName = fieldMetadata.getTabName();
                String groupName = fieldMetadata.getGroupName();

                RawTabInfo rawTabInfo = rawEntityInfo.getTab(tabName);
                rawGroupInfo = rawTabInfo.getGroup(groupName);
                if (rawGroupInfo == null) {
                    rawGroupInfo = RawInfoCreator.createRawGroupInfo(classMetadata.getReadonlyGroupMetadataMap().get(groupName));
                    rawTabInfo.addGroup(rawGroupInfo);
                }
            }

            Collection<IFieldInfo> fieldInfos = FieldInfoBuilder.createFieldInfos(topClassMetadata, fieldMetadata, collectionTypeReferenced);
            for(IFieldInfo fi : fieldInfos){
                if(fi.ignored())
                    continue;
                rawEntityInfo.addField(fi);
                String fieldName = fi.getName();
                if(fi instanceof IBasicFieldInfo){
                    IBasicFieldInfo bfi = (IBasicFieldInfo) fi;
                    if(bfi.isGridVisible()){
                        rawEntityInfo.addGridField(fieldName);
                    }
                }
                rawGroupInfo.addField(fieldName);
            }
        }
        String idFieldName = classMetadata.getIdFieldName();
        if(StringUtils.isNotEmpty(idFieldName)){
            rawEntityInfo.addGridField(idFieldName);
            rawEntityInfo.setIdField(idFieldName);
        }
        rawEntityInfo.setNameField(classMetadata.getNameFieldName());
        rawEntityInfo.addReferencingEntries(collectionTypeReferenced);
        rawEntityInfo.finishWriting();
    }

    private static class RawInfoCreator {
        static RawEntityInfo createRawEntityInfo(IClassMetadata classMetadata) {
            RawEntityInfo rawEntityInfo = new RawEntityInfoImpl();
            copyFriendlyMetadata(classMetadata, rawEntityInfo);
            return rawEntityInfo;
        }

        static RawGroupInfo createRawGroupInfo(GroupMetadata groupMetadata) {
            RawGroupInfo rawGroupInfo = new RawGroupInfoImpl();
            copyOrderedFriendlyMetadata(groupMetadata, rawGroupInfo);
            return rawGroupInfo;
        }

        static RawTabInfo createRawTabInfo(TabMetadata tabMetadata) {
            RawTabInfo rawTabInfo = new RawTabInfoImpl();
            copyOrderedFriendlyMetadata(tabMetadata, rawTabInfo);
            return rawTabInfo;
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
