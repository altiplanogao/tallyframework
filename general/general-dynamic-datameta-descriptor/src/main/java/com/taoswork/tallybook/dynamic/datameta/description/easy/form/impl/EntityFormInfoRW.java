package com.taoswork.tallybook.dynamic.datameta.description.easy.form.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.EntityFormInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityFormInfoRW extends NamedInfoRW, EntityFormInfo {
    void addTab(TabFormInfoRW tabFormInfo);

    void addField(FieldInfo fieldInfo);
}
