package com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabInfo extends NamedInfo {
    GroupInfo getGroup(String groupName);

    Collection<? extends GroupInfo> getGroups();
}
