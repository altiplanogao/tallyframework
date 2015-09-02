package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedOrderedInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawGroupInsight extends NamedOrderedInfo {
    Collection<String> getFields();
}
