package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityGridInfoRW extends NamedInfoRW, EntityGridInfo {

    void addField(FieldInfo fieldInfo);

    EntityGridInfoRW setPrimarySearchField(String primarySearchField);
}
