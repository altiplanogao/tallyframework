package com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface NamedInfoRW extends NamedInfo {
    NamedInfoRW setName(String name);

    NamedInfoRW setFriendlyName(String friendlyName);

    NamedInfoRW setOrder(int order);

    void copyNamedInfo(NamedInfo source);

}
