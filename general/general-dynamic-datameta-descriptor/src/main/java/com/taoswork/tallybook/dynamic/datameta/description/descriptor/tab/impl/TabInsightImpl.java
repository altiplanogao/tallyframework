package com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInsight;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl.GroupInsightRW;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class TabInsightImpl
        extends NamedOrderedInfoImpl
        implements TabInsightRW {

    private final Map<String, GroupInsightRW> groups = new HashMap<String, GroupInsightRW>();

    @Override
    public void addGroup(GroupInsightRW groupInfo) {
        groups.put(groupInfo.getName(), groupInfo);
    }

    @Override
    public GroupInsightRW getGroupRW(String groupName) {
        return groups.getOrDefault(groupName, null);
    }

    @Override
    public GroupInsight getGroup(final String groupName) {
        return groups.getOrDefault(groupName, null);
    }

    @Override
    public Collection<? extends GroupInsight> getGroups() {
        return Collections.unmodifiableCollection(groups.values());
    }
}
