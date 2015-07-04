package com.taoswork.tallybook.dynamic.datameta.description.descriptor.base;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public interface IEntityInfo extends NamedInfo, Serializable{

    Collection<? extends FieldInfo> getFields();

    EntityInfoType getInfoType();

}
