package com.taoswork.tallybook.dynamic.datameta.description.descriptor.group;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface GroupInfo extends NamedInfo {
    FieldInfo getField(String fieldName);

    Collection<? extends FieldInfo> getFields();
}
