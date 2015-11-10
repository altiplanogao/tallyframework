package com.taoswork.tallybook.dynamic.datameta.description.service.impl;

import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
@ThreadSafe
public class MetaInfoServiceImpl implements
    MetaInfoService {
    private static Logger LOGGER = LoggerFactory.getLogger(MetaInfoServiceImpl.class);

    @Override
    public IEntityInfo generateEntityInfo(IClassMetadata classMetadata, EntityInfoType infoType) {
        EntityInfo entityInfo = generateEntityMainInfo(classMetadata);
        return convert(entityInfo, infoType);
    }

    @Override
    public EntityInfo generateEntityMainInfo(IClassMetadata classMetadata) {
        EntityInfo entityInfo = EntityInfoBuilder.build(classMetadata);
        return entityInfo;
    }

    @Override
    public IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type) {
        Class<? extends IEntityInfo> cls = type.getEntityInfoClass();
        if (cls != null) {
            try {
                Constructor cons = cls.getConstructor(new Class[]{EntityInfo.class});
                IEntityInfo cinfo = (IEntityInfo) cons.newInstance(entityInfo);
                return cinfo;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return null;
    }
}
