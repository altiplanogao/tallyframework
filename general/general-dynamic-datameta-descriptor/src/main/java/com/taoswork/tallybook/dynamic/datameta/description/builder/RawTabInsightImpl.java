package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
final class RawTabInsightImpl
    extends NamedOrderedInfoImpl
    implements RawTabInsightRW {

    /**
     * groups are not ordered
     */
    private final Map<String, RawGroupInsightRW> groups = new HashMap<String, RawGroupInsightRW>();

    @Override
    public void addGroup(RawGroupInsightRW groupInfo) {
        groups.put(groupInfo.getName(), groupInfo);
    }

    @Override
    public RawGroupInsightRW getGroupRW(String groupName) {
        return groups.get(groupName);
    }

    @Override
    public RawGroupInsight getGroup(final String groupName) {
        return groups.get(groupName);
    }

    @Override
    public Collection<? extends RawGroupInsight> getGroups() {
        return Collections.unmodifiableCollection(groups.values());
    }
}
