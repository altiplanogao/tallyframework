package com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInsight;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabInsight extends NamedOrderedInfo {
    GroupInsight getGroup(String groupName);

    Collection<? extends GroupInsight> getGroups();
}
