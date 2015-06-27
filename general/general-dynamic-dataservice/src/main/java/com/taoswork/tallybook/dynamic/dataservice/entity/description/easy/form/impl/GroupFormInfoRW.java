package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.GroupFormInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface GroupFormInfoRW extends NamedInfoRW, GroupFormInfo {
    void addField(FieldInfo fieldInfo);
}
