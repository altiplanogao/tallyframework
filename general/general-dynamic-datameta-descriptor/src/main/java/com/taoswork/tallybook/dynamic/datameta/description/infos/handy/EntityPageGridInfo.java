package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class EntityPageGridInfo extends EntityGridInfo {
    public EntityPageGridInfo(EntityInfo entityInfo) {
        super(entityInfo);
        primarySearchFieldFriendly = entityInfo.getField(this.primarySearchField).getFriendlyName();
    }

    public final String primarySearchFieldFriendly;

    @Override
    public String getType() {
        return EntityInfoType.PageGrid.getName();
    }
}
