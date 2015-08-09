package com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl.GroupInsightRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInsight;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabInsightRW
        extends NamedOrderedInfoRW, TabInsight {
    void addGroup(GroupInsightRW groupInfoByComp);
    GroupInsightRW getGroupRW(String groupName);

}
