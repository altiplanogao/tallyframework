package com.taoswork.tallybook.dynamic.datameta.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoImpl;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.GroupFormInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class TabFormInfoImpl
        extends NamedInfoImpl
        implements TabFormInfoRW {

        private final Set<GroupFormInfo> groups = new TreeSet<GroupFormInfo>(new InfoOrderedComparator());

        @Override
        public Collection<GroupFormInfo> getGroups() {
                return Collections.unmodifiableCollection(groups);
        }

        @Override
        public void addGroup(GroupFormInfoRW groupFormInfo) {
                groups.add(groupFormInfo);
        }
}
