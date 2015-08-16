package com.taoswork.tallybook.dynamic.dataservice.core.description.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public class FriendlyMetaInfoServiceImpl implements FriendlyMetaInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendlyMetaInfoServiceImpl.class);

    @Resource(name = FriendlyMetaInfoService.MESSAGE_SOURCE_BEAN_NAME)
    private MessageSource friendlyMessageSource;

    @Override
    public EntityInfo makeFriendly(EntityInfo rawEntityInfo, Locale locale) {
        EntityInfo freshEntityInfo = CloneUtility.makeClone(rawEntityInfo);
        if (freshEntityInfo instanceof NamedInfoRW){
            String oldFriendly = freshEntityInfo.getFriendlyName();
            String newFriendly = friendlyMessageSource.getMessage(oldFriendly, null, oldFriendly, locale);

            NamedInfoRW namedInfo = (NamedInfoRW)freshEntityInfo;
            namedInfo.setFriendlyName(newFriendly);
        }else {
            LOGGER.error("new EntityInfo by Clone has un-writeable IEntityInfo {}", freshEntityInfo);
        }
        for (FieldInfo fieldInfo : freshEntityInfo.getFields().values()) {
            String oldFriendly = fieldInfo.getFriendlyName();
            String newFriendly = friendlyMessageSource.getMessage(oldFriendly, null, oldFriendly, locale);
            if (fieldInfo instanceof FieldInfoRW) {
                FieldInfoRW fieldInfoRW = (FieldInfoRW) fieldInfo;
                fieldInfoRW.setFriendlyName(newFriendly);
            } else {
                LOGGER.error("new EntityInfo by Clone has un-writeable FieldInfo {}", fieldInfo);
            }
        }
        for(ITabInfo tabInfo : freshEntityInfo.getTabs()){
            if (tabInfo instanceof NamedInfoRW) {
                String oldFriendly = tabInfo.getFriendlyName();
                String newFriendly = friendlyMessageSource.getMessage(oldFriendly, null, oldFriendly, locale);
                NamedInfoRW tabInfoRW = (NamedInfoRW) tabInfo;
                tabInfoRW.setFriendlyName(newFriendly);
            } else {
                LOGGER.error("new EntityInfo by Clone has un-writeable FieldInfo {}", tabInfo);
            }
            for(IGroupInfo groupInfo : tabInfo.getGroups()){
                if (groupInfo instanceof NamedInfoRW) {
                    String oldFriendly = groupInfo.getFriendlyName();
                    String newFriendly = friendlyMessageSource.getMessage(oldFriendly, null, oldFriendly, locale);
                    NamedInfoRW groupInfoRW = (NamedInfoRW) groupInfo;
                    groupInfoRW.setFriendlyName(newFriendly);
                } else {
                    LOGGER.error("new EntityInfo by Clone has un-writeable FieldInfo {}", tabInfo);
                }
            }
        }

        return freshEntityInfo;
    }
}
