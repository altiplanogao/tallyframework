package com.taoswork.tallybook.descriptor.description.builder;

import com.taoswork.tallybook.descriptor.description.descriptor.base.impl.NamedOrderedInfoImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
final class RawTabInfoImpl
        extends NamedOrderedInfoImpl
        implements RawTabInfo {

    /**
     * groups are not ordered
     */
    private final Map<String, RawGroupInfo> groups = new HashMap<String, RawGroupInfo>();

    @Override
    public void addGroup(RawGroupInfo groupInfo) {
        groups.put(groupInfo.getName(), groupInfo);
    }

    @Override
    public RawGroupInfo getGroup(final String groupName) {
        return groups.get(groupName);
    }

    @Override
    public Collection<? extends RawGroupInfo> getGroups() {
        return Collections.unmodifiableCollection(groups.values());
    }
}
