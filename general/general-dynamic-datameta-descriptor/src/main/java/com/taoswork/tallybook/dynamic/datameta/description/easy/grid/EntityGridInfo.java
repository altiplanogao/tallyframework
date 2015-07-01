package com.taoswork.tallybook.dynamic.datameta.description.easy.grid;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface EntityGridInfo extends IEntityInfo {

    Collection<FieldInfo> getFields();

    String getPrimarySearchField();

    String fetchPrimarySearchFieldFriendlyName();
}
