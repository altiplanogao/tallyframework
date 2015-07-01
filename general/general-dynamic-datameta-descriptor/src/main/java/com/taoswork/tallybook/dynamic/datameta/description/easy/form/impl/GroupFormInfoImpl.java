package com.taoswork.tallybook.dynamic.datameta.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class GroupFormInfoImpl
        extends NamedInfoImpl
        implements GroupFormInfoRW{

        private final Set<FieldInfo> fields = new TreeSet<FieldInfo>(new InfoOrderedComparator());

        @Override
        public Collection<FieldInfo> getFields() {
                return Collections.unmodifiableCollection(fields);
        }

        @Override
        public void addField(FieldInfo fieldInfo) {
                fields.add(fieldInfo);
        }
}
