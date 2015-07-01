package com.taoswork.tallybook.dynamic.datameta.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.GroupFormInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface GroupFormInfoRW extends NamedInfoRW, GroupFormInfo {
    void addField(FieldInfo fieldInfo);
}
