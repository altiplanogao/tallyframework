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
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
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
        final Class<?> entityClass = classMetadata.getEntityClz();

        //gather tab/group info
        final Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        final Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        final Map<String, GroupMetadata> groupMetadataMap = classMetadata.getReadonlyGroupMetadataMap();
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            if(fieldMetadata.getIgnored())
                continue;
            String tabName = fieldMetadata.getTabName();
            RawTabInfo rawTabInfo = rawEntityInfo.getTab(tabName);
            if(rawTabInfo == null){
                TabMetadata tabMetadata = tabMetadataMap.get(tabName);
                if(tabMetadata != null){
                    rawTabInfo = RawInfoCreator.createRawTabInfo(tabMetadata);
                }else {
                    rawTabInfo = RawInfoCreator.createRawTabInfo(entityClass, tabName);
                }
                rawEntityInfo.addTab(rawTabInfo);
            }

            String groupName = fieldMetadata.getGroupName();
            RawGroupInfo rawGroupInfo = rawTabInfo.getGroup(groupName);
            if(rawGroupInfo == null){
                GroupMetadata groupMetadata = groupMetadataMap.get(groupName);
                if(groupMetadata != null){
                    rawGroupInfo = RawInfoCreator.createRawGroupInfo(groupMetadata);
                }else {
                    rawGroupInfo = RawInfoCreator.createRawGroupInfo(entityClass, groupName);
                }
                rawTabInfo.addGroup(rawGroupInfo);
            }
        }

        //add fields
        for (Map.Entry<String, IFieldMetadata> fieldMetadataEntry : fieldMetadataMap.entrySet()) {
            IFieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            if(fieldMetadata.getIgnored())
                continue;
            String tabName = fieldMetadata.getTabName();
            String groupName = fieldMetadata.getGroupName();
            RawTabInfo rawTabInfo = rawEntityInfo.getTab(tabName);
            RawGroupInfo rawGroupInfo = rawTabInfo.getGroup(groupName);

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

        static RawGroupInfo createRawGroupInfo(Class entityCls, String groupName) {
            RawGroupInfo rawGrpInfo = new RawGroupInfoImpl();
            rawGrpInfo.setName(groupName)
                .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassGroup(entityCls, groupName));
            rawGrpInfo.setOrder(PresentationClass.Group.DEFAULT_ORDER);
            return rawGrpInfo;
        }

        static RawTabInfo createRawTabInfo(TabMetadata tabMetadata) {
            RawTabInfo rawTabInfo = new RawTabInfoImpl();
            copyOrderedFriendlyMetadata(tabMetadata, rawTabInfo);
            return rawTabInfo;
        }

        static RawTabInfo createRawTabInfo(Class entityCls, String tabName) {
            RawTabInfo rawTabInfo = new RawTabInfoImpl();
            rawTabInfo.setName(tabName)
                .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassTab(entityCls, tabName));
            rawTabInfo.setOrder(PresentationClass.Tab.DEFAULT_ORDER);
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
