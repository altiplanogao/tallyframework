package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.NamedInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabFormInfo extends NamedInfo {
    Collection<GroupFormInfo> getGroups();
}
