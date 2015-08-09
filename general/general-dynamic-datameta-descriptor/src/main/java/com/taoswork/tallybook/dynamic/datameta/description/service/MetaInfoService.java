package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaInfoService {
    public static final String SERVICE_NAME = "MetaInfoService";

    EntityInfo generateEntityMainInfo(ClassMetadata classMetadata);

    IEntityInfo generateEntityInfo(ClassMetadata classMetadata, EntityInfoType infoType);
//
//    EntityFullInfo generateEntityFullInfo(ClassMetadata classMetadata);
//
//    EntityGridInfo generateEntityGridInfo(ClassMetadata classMetadata);
//
//    EntityFormInfo generateEntityFormInfo(ClassMetadata classMetadata);

    IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type);

}
