package com.taoswork.tallybook.dynamic.datameta.description.easy.grid.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityGridInfoRW extends NamedInfoRW, EntityGridInfo {

    void addField(FieldInfo fieldInfo);

    EntityGridInfoRW setPrimarySearchField(String primarySearchField);
}
