package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.TabFormInfo;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityFormInfoImpl
        extends NamedInfoImpl
        implements EntityFormInfoRW {

    private Map<String, FieldInfo> fields = new HashMap<String, FieldInfo>();
    private Set<TabFormInfoRW> tabs = new TreeSet<TabFormInfoRW>(new InfoOrderedComparator());

    @Override
    public FieldInfo getField(String fieldName) {
        return fields.getOrDefault(fieldName, null);
    }

    @Override
    public Collection<FieldInfo> getFields() {
        return Collections.unmodifiableCollection(fields.values());
    }

    @Override
    public Collection<? extends TabFormInfo> getTabs() {
        return Collections.unmodifiableCollection(tabs);
    }

    @Override
    public void addTab(TabFormInfoRW tabFormInfo) {
        tabs.add(tabFormInfo);
    }

    @Override
    public void addField(FieldInfo fieldInfo) {
        fields.put(fieldInfo.getName(), fieldInfo);
    }
}
