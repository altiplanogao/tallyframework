package com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.tab.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.group.GroupInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.group.impl.GroupInfoRW;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class TabInfoImpl
        extends NamedInfoImpl
        implements TabInfoRW {

    private final Map<String, GroupInfoRW> groups = new HashMap<String, GroupInfoRW>();

    @Override
    public void addGroup(GroupInfoRW groupInfo) {
        groups.put(groupInfo.getName(), groupInfo);
    }

    @Override
    public GroupInfoRW getGroupRW(String groupName) {
        return groups.getOrDefault(groupName, null);
    }

    @Override
    public GroupInfo getGroup(final String groupName) {
        return groups.getOrDefault(groupName, null);
    }

    @Override
    public Collection<? extends GroupInfo> getGroups() {
        return Collections.unmodifiableCollection(groups.values());
    }
}
