package com.taoswork.tallybook.descriptor.description.infos.handy;

import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
abstract class _BaseEntityHandyInfo extends NamedInfoImpl implements IEntityInfo {
    private final boolean containsHierarchy;
    private final String entityType;

    protected _BaseEntityHandyInfo(EntityInfo entityInfo) {
        this.copyNamedInfo(entityInfo);
        this.containsHierarchy = entityInfo.isWithHierarchy();
        this.entityType = entityInfo.getType();
    }

    @Override
    public boolean isWithHierarchy() {
        return containsHierarchy;
    }

    @Override
    public String getType() {
        return entityType;
    }
}
