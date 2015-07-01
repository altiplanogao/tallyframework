package com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.impl.FieldInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.GroupInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface GroupInfoRW extends NamedInfoRW, GroupInfo {
    void addField(FieldInfoRW fieldInfo);

    FieldInfoRW getFieldRW(final String fieldName);
}
