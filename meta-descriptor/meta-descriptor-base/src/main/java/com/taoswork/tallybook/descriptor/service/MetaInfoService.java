package com.taoswork.tallybook.descriptor.service;

import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaInfoService {
    public static final String SERVICE_NAME = "MetaInfoService";

    EntityInfo generateEntityMainInfo(IClassMeta classMeta);

    IEntityInfo generateEntityInfo(IClassMeta classMeta, EntityInfoType infoType);

    IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type);

}
