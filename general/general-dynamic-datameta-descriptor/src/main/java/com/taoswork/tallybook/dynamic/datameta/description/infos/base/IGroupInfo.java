package com.taoswork.tallybook.dynamic.datameta.description.infos.base;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface IGroupInfo extends NamedOrderedInfo {
    List<String> getFields();
}
