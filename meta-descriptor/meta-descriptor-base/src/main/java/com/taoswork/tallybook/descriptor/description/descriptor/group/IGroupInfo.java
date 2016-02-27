package com.taoswork.tallybook.descriptor.description.descriptor.group;

import com.taoswork.tallybook.descriptor.description.descriptor.base.NamedOrderedInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface IGroupInfo extends NamedOrderedInfo {
    List<String> getFields();
}
