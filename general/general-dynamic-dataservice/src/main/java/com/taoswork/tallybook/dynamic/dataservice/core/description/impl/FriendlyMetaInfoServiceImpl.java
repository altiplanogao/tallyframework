package com.taoswork.tallybook.dynamic.dataservice.core.description.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.BooleanFacetInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.EnumFacetInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
@ThreadSafe
public class FriendlyMetaInfoServiceImpl implements FriendlyMetaInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendlyMetaInfoServiceImpl.class);

    @Resource(name = FriendlyMetaInfoService.MESSAGE_SOURCE_BEAN_NAME)
    private MessageSource entityMessageSource;

    @Override
    public EntityInfo makeFriendly(EntityInfo rawEntityInfo, Locale locale) {
        EntityInfo freshEntityInfo = CloneUtility.makeClone(rawEntityInfo);
        if (freshEntityInfo instanceof NamedInfoRW){
            makeNamedInfoFriendly(freshEntityInfo, (NamedInfoRW) freshEntityInfo, locale);
        }else {
            LOGGER.error("new EntityInfo by Clone has un-writeable IEntityInfo {}", freshEntityInfo);
        }
        for (FieldInfo fieldInfo : freshEntityInfo.getFields().values()) {
            makeFieldInfoFriendly(fieldInfo, locale);
        }
        for(ITabInfo tabInfo : freshEntityInfo.getTabs()){
            if (tabInfo instanceof NamedInfoRW) {
                makeNamedInfoFriendly(tabInfo, (NamedInfoRW)tabInfo, locale);
            } else {
                LOGGER.error("new EntityInfo by Clone has un-writeable FieldInfo {}", tabInfo);
            }
            for(IGroupInfo groupInfo : tabInfo.getGroups()){
                if (groupInfo instanceof NamedInfoRW) {
                    makeNamedInfoFriendly(groupInfo, (NamedInfoRW)groupInfo, locale);
                } else {
                    LOGGER.error("new EntityInfo by Clone has un-writeable FieldInfo {}", tabInfo);
                }
            }
        }

        return freshEntityInfo;
    }

    private void makeNamedInfoFriendly(NamedInfo source, NamedInfoRW target, Locale locale){
        String oldFriendly = source.getFriendlyName();
        String newFriendly = entityMessageSource.getMessage(oldFriendly, null, oldFriendly, locale);
        if(oldFriendly.equals(newFriendly)){
            int underscore = oldFriendly.indexOf("_");
            if(underscore > 1){
                String fallbackKey = oldFriendly.substring(underscore + 1);
                String fallbackValue =  entityMessageSource.getMessage(fallbackKey, null, fallbackKey, locale);
                if(!fallbackKey.equals(fallbackValue)){
                    newFriendly = fallbackValue;
                }
            }
        }
        target.setFriendlyName(newFriendly);
    }

    private void makeFieldInfoFriendly(FieldInfo fieldInfo, Locale locale){
        if (fieldInfo instanceof FieldInfoRW) {
            makeNamedInfoFriendly(fieldInfo, (NamedInfoRW)fieldInfo, locale);

            EnumFacetInfo enumFacetInfo = (EnumFacetInfo)fieldInfo.getFacet(FieldFacetType.Enum);
            if(enumFacetInfo != null){
                EnumFacetInfo freshEnumFacetInfo = CloneUtility.makeClone(enumFacetInfo);
                for(String key : freshEnumFacetInfo.getOptions()){
                    String val = enumFacetInfo.getFriendlyName(key);
                    String newVal = entityMessageSource.getMessage(val, null, val, locale);
                    freshEnumFacetInfo.setFriendlyName(key, newVal);
                }
                ((FieldInfoRW)fieldInfo).addFacet(freshEnumFacetInfo);
            }

            BooleanFacetInfo booleanFacetInfo = (BooleanFacetInfo)fieldInfo.getFacet(FieldFacetType.Boolean);
            if(booleanFacetInfo != null){
                BooleanFacetInfo freshBooleanFacetInfo = CloneUtility.makeClone(booleanFacetInfo);
                for(int x = 0 ; x < 2 ; ++x){
                    boolean v = (x == 0);
                    String val = booleanFacetInfo.getOption(v);
                    String newVal = entityMessageSource.getMessage(val, null, val, locale);
                    freshBooleanFacetInfo.setOptionFor(v, newVal);
                }
                ((FieldInfoRW)fieldInfo).addFacet(freshBooleanFacetInfo);
            }
        } else {
            LOGGER.error("new EntityInfo by Clone has un-writeable FieldInfo {}", fieldInfo);
        }
    }
}
