package com.taoswork.tallybook.dataservice.core.description.impl;

import com.taoswork.tallybook.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.descriptor.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typed.BooleanFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typed.EnumFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.group.IGroupInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
        Set<EntityInfo> entityInfoList = new HashSet<EntityInfo>();
        entityInfoList.add(freshEntityInfo);
        Map<String, EntityInfo> entryInfos = freshEntityInfo.getReferencingInfos();
        if (entryInfos != null) {
            for (Map.Entry<String, EntityInfo> entry : entryInfos.entrySet()) {
                entityInfoList.add(entry.getValue());
            }
        }

        for (EntityInfo entityInfo : entityInfoList) {
            makeEntityInfoFriendly(entityInfo, locale);
        }
        return freshEntityInfo;
    }

    private void makeEntityInfoFriendly(EntityInfo entityInfo, Locale locale) {
        if (entityInfo instanceof NamedInfoRW) {
            makeNamedInfoFriendly(entityInfo, (NamedInfoRW) entityInfo, locale);
        } else {
            LOGGER.error("new EntityInfo by Clone has un-writeable IEntityInfo {}", entityInfo);
        }
        for (IFieldInfo fieldInfo : entityInfo.getFields().values()) {
            makeFieldInfoFriendly(fieldInfo, locale);
        }
        for (ITabInfo tabInfo : entityInfo.getTabs()) {
            if (tabInfo instanceof NamedInfoRW) {
                makeNamedInfoFriendly(tabInfo, (NamedInfoRW) tabInfo, locale);
            } else {
                LOGGER.error("new EntityInfo by Clone has un-writeable IFieldInfo {}", tabInfo);
            }
            for (IGroupInfo groupInfo : tabInfo.getGroups()) {
                if (groupInfo instanceof NamedInfoRW) {
                    makeNamedInfoFriendly(groupInfo, (NamedInfoRW) groupInfo, locale);
                } else {
                    LOGGER.error("new EntityInfo by Clone has un-writeable IFieldInfo {}", tabInfo);
                }
            }
        }
    }

    private void makeNamedInfoFriendly(NamedInfo source, NamedInfoRW target, Locale locale) {
        String oldFriendly = source.getFriendlyName();
        String newFriendly = entityMessageSource.getMessage(oldFriendly, null, oldFriendly, locale);
        if (oldFriendly.equals(newFriendly)) {
            int underscore = oldFriendly.indexOf("_");
            if (underscore > 1) {
                String fallbackKey = oldFriendly.substring(underscore + 1);
                String fallbackValue = entityMessageSource.getMessage(fallbackKey, null, fallbackKey, locale);
                if (!fallbackKey.equals(fallbackValue)) {
                    newFriendly = fallbackValue;
                }
            }
        }
        target.setFriendlyName(newFriendly);
    }

    private void makeFieldInfoFriendly(IFieldInfo fieldInfo, Locale locale) {
        if (fieldInfo instanceof IFieldInfoRW) {
            makeNamedInfoFriendly(fieldInfo, (NamedInfoRW) fieldInfo, locale);
        } else {
            LOGGER.error("new EntityInfo by Clone has un-writeable IFieldInfo {}", fieldInfo);
        }
        if (fieldInfo instanceof EnumFieldInfo) {
            EnumFieldInfo enumFieldInfo = (EnumFieldInfo) fieldInfo;
            EnumFieldInfo clone = CloneUtility.makeClone(enumFieldInfo);
            for (String key : clone.getOptions()) {
                String val = clone.getFriendlyName(key);
                String newVal = entityMessageSource.getMessage(val, null, val, locale);
                enumFieldInfo.setFriendlyName(key, newVal);
            }
        } else if (fieldInfo instanceof BooleanFieldInfo) {
            BooleanFieldInfo booleanFieldInfo = (BooleanFieldInfo) fieldInfo;
            BooleanFieldInfo clone = CloneUtility.makeClone(booleanFieldInfo);
            for (int x = 0; x < 2; ++x) {
                boolean v = (x == 0);
                String val = clone.getOption(v);
                String newVal = entityMessageSource.getMessage(val, null, val, locale);
                booleanFieldInfo.setOptionFor(v, newVal);
            }
        }
    }
}
