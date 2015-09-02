package com.taoswork.tallybook.dynamic.datameta.description.infos.base.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class TabInfoImpl extends NamedOrderedInfoImpl implements ITabInfo {
    /**
     * groups are ordered
     */
    private final List<IGroupInfo> groups;

    public TabInfoImpl(List<IGroupInfo> groups){
        this.groups = NamedOrderedInfo.NameSorter.makeObjectOrdered(groups);
    }

    @Override
    public List<IGroupInfo> getGroups() {
        return groups;
    }
}
