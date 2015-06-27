package com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.group.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.impl.FieldInfoRW;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class GroupInfoImpl
        extends NamedInfoImpl
        implements GroupInfoRW {

    private final Map<String, FieldInfoRW> fields = new HashMap<String, FieldInfoRW>();

    @Override
    public FieldInfo getField(final String fieldName) {
        return fields.getOrDefault(fieldName, null);
    }

    @Override
    public FieldInfoRW getFieldRW(final String fieldName) {
        return fields.getOrDefault(fieldName, null);
    }

    @Override
    public Collection<? extends FieldInfo> getFields() {
        return Collections.unmodifiableCollection(fields.values());
    }

    @Override
    public void addField(FieldInfoRW fieldInfo) {
        fields.put(fieldInfo.getName(), fieldInfo);
    }
}
