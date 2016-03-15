package com.taoswork.tallybook.descriptor.description.builder;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2IPool;
import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IBasicFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.GroupMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.TabMeta;
import com.taoswork.tallybook.descriptor.metadata.friendly.IFriendly;
import com.taoswork.tallybook.descriptor.metadata.friendly.IFriendlyOrdered;
import com.taoswork.tallybook.descriptor.metadata.utils.FriendlyNameHelper;
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
    private final FM2IPool fm2IPool;

    public RawEntityInfoBuilder(FM2IPool fm2IPool) {
        this.fm2IPool = fm2IPool;
    }

    private RawEntityInfoBuilder() throws IllegalAccessException {
        throw new IllegalAccessException("RawEntityInfoBuilder: Not instance-able object");
    }

    /**
     * Convert ClassMetadata object to RawEntityInfo object,
     * It is the first step of entity information sorting.
     *
     * @param classMeta
     * @return
     */
    public RawEntityInfo buildRawEntityInfo(IClassMeta classMeta) {
        final RawEntityInfo rawEntityInfo = RawInfoCreator.createRawEntityInfo(classMeta);
        rawEntityInfoAppendMetadata(rawEntityInfo, classMeta);
        return rawEntityInfo;
    }

    private void rawEntityInfoAppendMetadata(RawEntityInfo rawEntityInfo, final IClassMeta classMeta) {
        Collection<Class> collectionTypeReferenced = new HashSet<Class>();

        final IClassMeta topMeta = classMeta;
        final Class<?> entityClass = classMeta.getEntityClz();

        //gather tab/group info
        final Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        final Map<String, TabMeta> tabMetadataMap = classMeta.getReadonlyTabMetaMap();
        final Map<String, GroupMeta> groupMetadataMap = classMeta.getReadonlyGroupMetaMap();
        for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
            IFieldMeta fieldMeta = fieldMetaEntry.getValue();
            if (fieldMeta.getIgnored())
                continue;
            String tabName = fieldMeta.getTabName();
            RawTabInfo rawTabInfo = rawEntityInfo.getTab(tabName);
            if (rawTabInfo == null) {
                TabMeta tabMeta = tabMetadataMap.get(tabName);
                if (tabMeta != null) {
                    rawTabInfo = RawInfoCreator.createRawTabInfo(tabMeta);
                } else {
                    rawTabInfo = RawInfoCreator.createRawTabInfo(entityClass, tabName);
                }
                rawEntityInfo.addTab(rawTabInfo);
            }

            String groupName = fieldMeta.getGroupName();
            RawGroupInfo rawGroupInfo = rawTabInfo.getGroup(groupName);
            if (rawGroupInfo == null) {
                GroupMeta groupMeta = groupMetadataMap.get(groupName);
                if (groupMeta != null) {
                    rawGroupInfo = RawInfoCreator.createRawGroupInfo(groupMeta);
                } else {
                    rawGroupInfo = RawInfoCreator.createRawGroupInfo(entityClass, groupName);
                }
                rawTabInfo.addGroup(rawGroupInfo);
            }
        }

        //add fields
        for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
            IFieldMeta fieldMeta = fieldMetaEntry.getValue();
            if (fieldMeta.getIgnored())
                continue;
            String tabName = fieldMeta.getTabName();
            String groupName = fieldMeta.getGroupName();
            RawTabInfo rawTabInfo = rawEntityInfo.getTab(tabName);
            RawGroupInfo rawGroupInfo = rawTabInfo.getGroup(groupName);

            Collection<IFieldInfo> fieldInfos = new FieldInfoBuilder(fm2IPool).createFieldInfos(topMeta, fieldMeta, collectionTypeReferenced);
            for (IFieldInfo fi : fieldInfos) {
                if (fi.ignored())
                    continue;
                rawEntityInfo.addField(fi);
                String fieldName = fi.getName();
                if (fi instanceof IBasicFieldInfo) {
                    IBasicFieldInfo bfi = (IBasicFieldInfo) fi;
                    if (bfi.isGridVisible()) {
                        rawEntityInfo.addGridField(fieldName);
                    }
                }
                rawGroupInfo.addField(fieldName);
            }
        }
        String idFieldName = classMeta.getIdFieldName();
        if (StringUtils.isNotEmpty(idFieldName)) {
            rawEntityInfo.addGridField(idFieldName);
            rawEntityInfo.setIdField(idFieldName);
        }
        rawEntityInfo.setNameField(classMeta.getNameFieldName());
        rawEntityInfo.addReferencingEntries(collectionTypeReferenced);
        rawEntityInfo.finishWriting();
    }

    private static class RawInfoCreator {
        static RawEntityInfo createRawEntityInfo(IClassMeta classMeta) {
            RawEntityInfo rawEntityInfo = new RawEntityInfoImpl();
            copyFriendlyMetadata(classMeta, rawEntityInfo);
            return rawEntityInfo;
        }

        static RawGroupInfo createRawGroupInfo(GroupMeta groupMeta) {
            RawGroupInfo rawGroupInfo = new RawGroupInfoImpl();
            copyOrderedFriendlyMetadata(groupMeta, rawGroupInfo);
            return rawGroupInfo;
        }

        static RawGroupInfo createRawGroupInfo(Class entityCls, String groupName) {
            RawGroupInfo rawGrpInfo = new RawGroupInfoImpl();
            rawGrpInfo.setName(groupName)
                    .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassGroup(entityCls, groupName));
            rawGrpInfo.setOrder(PresentationClass.Group.DEFAULT_ORDER);
            return rawGrpInfo;
        }

        static RawTabInfo createRawTabInfo(TabMeta tabMeta) {
            RawTabInfo rawTabInfo = new RawTabInfoImpl();
            copyOrderedFriendlyMetadata(tabMeta, rawTabInfo);
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
