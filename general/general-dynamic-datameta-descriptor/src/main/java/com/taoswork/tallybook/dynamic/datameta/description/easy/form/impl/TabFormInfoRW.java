package com.taoswork.tallybook.dynamic.datameta.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.TabFormInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface TabFormInfoRW extends NamedInfoRW, TabFormInfo {
    void addGroup(GroupFormInfoRW groupFormInfo);
}
