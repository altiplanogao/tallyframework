package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.EntityFormInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityFormInfoRW extends NamedInfoRW, EntityFormInfo {
    void addTab(TabFormInfoRW tabFormInfo);

    void addField(FieldInfo fieldInfo);
}
