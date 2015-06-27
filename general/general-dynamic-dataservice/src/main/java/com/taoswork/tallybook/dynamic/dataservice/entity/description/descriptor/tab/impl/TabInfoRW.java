package com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.tab.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.group.impl.GroupInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.tab.TabInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabInfoRW
        extends NamedInfoRW, TabInfo {
    void addGroup(GroupInfoRW groupInfoByComp);
    GroupInfoRW getGroupRW(String groupName);

}
