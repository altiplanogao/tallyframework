package com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface GroupFormInfo extends NamedInfo {
    Collection<FieldInfo> getFields();
}
