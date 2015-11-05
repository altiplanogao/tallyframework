package com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.IGroupInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface ITabInfo extends NamedOrderedInfo {
    List<IGroupInfo> getGroups();
}
