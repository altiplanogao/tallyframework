package com.taoswork.tallybook.dynamic.datameta.description.easy.form;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabFormInfo extends NamedInfo {
    Collection<GroupFormInfo> getGroups();
}
