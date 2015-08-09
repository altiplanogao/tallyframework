package com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedOrderedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInsight;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface GroupInsightRW extends NamedOrderedInfoRW, GroupInsight {
    void clearFields();

    void addField(String field);

    void setFields(Collection<String> fields);
}
