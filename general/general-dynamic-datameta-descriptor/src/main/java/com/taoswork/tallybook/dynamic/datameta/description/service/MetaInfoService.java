package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaInfoService {
    public static final String SERVICE_NAME = "MetaInfoService";

    EntityInfo generateEntityMainInfo(IClassMetadata classMetadata);

    IEntityInfo generateEntityInfo(IClassMetadata classMetadata, EntityInfoType infoType);

    IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type);

}
