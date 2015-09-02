package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawTabInsightRW
        extends NamedOrderedInfoRW, RawTabInsight {
    void addGroup(RawGroupInsightRW groupInfoByComp);
    RawGroupInsightRW getGroupRW(String groupName);

}
