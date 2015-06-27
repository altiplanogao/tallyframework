package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityGridInfo extends IEntityInfo {

    Collection<FieldInfo> getFields();

    String getPrimarySearchField();

    String fetchPrimarySearchFieldFriendlyName();
}
