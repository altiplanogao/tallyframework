package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawGroupInsightRW extends NamedOrderedInfoRW, RawGroupInsight {
    void clearFields();

    void addField(String field);

    void setFields(Collection<String> fields);
}
