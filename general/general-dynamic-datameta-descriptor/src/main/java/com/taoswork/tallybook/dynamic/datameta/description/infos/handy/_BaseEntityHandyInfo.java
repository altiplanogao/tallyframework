package com.taoswork.tallybook.dynamic.datameta.description.infos.handy;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
abstract class _BaseEntityHandyInfo extends NamedInfoImpl implements IEntityInfo {
    private final boolean containsHierarchy;
    private final String entityType;

    protected _BaseEntityHandyInfo(EntityInfo entityInfo){
        this.copyNamedInfo(entityInfo);
        this.containsHierarchy = entityInfo.isContainsHierarchy();
        this.entityType = entityInfo.getEntityType();
    }

    @Override
    public boolean isContainsHierarchy() {
        return containsHierarchy;
    }

    @Override
    public String getEntityType() {
        return entityType;
    }
}
