package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaInfoService {
    public static final String SERVICE_NAME = "MetaInfoService";

    IEntityInfo generateEntityInfo(ClassMetadata classMetadata, EntityInfoType infoType);

    EntityInfo generateEntityInfo(ClassMetadata classMetadata);

    EntityGridInfo generateEntityGridInfo(ClassMetadata classMetadata);

    EntityFormInfo generateEntityFormInfo(ClassMetadata classMetadata);

}
