package com.taoswork.tallybook.dynamic.datameta.description.service.impl;

import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityFormInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityGridInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaInfoServiceImpl implements
        MetaInfoService {

    @Override
    public IEntityInfo generateEntityInfo(ClassMetadata classMetadata, EntityInfoType infoType) {
        if (EntityInfoType.Full.equals(infoType)) {
            return this.generateEntityInfo(classMetadata);
        } else if (EntityInfoType.Form.equals(infoType)) {
            return this.generateEntityFormInfo(classMetadata);
        } else if (EntityInfoType.Grid.equals(infoType)) {
            return this.generateEntityGridInfo(classMetadata);
        } else {
            return null;
        }
    }

    @Override
    public EntityInfo generateEntityInfo(ClassMetadata classMetadata) {
        EntityInfo entityInfo = EntityInfoBuilder.buildClassInfo(classMetadata);
        return entityInfo;
    }

    @Override
    public EntityGridInfo generateEntityGridInfo(ClassMetadata classMetadata) {
        EntityInfo classInfo = generateEntityInfo(classMetadata);
        return EntityGridInfoBuilder.build(classInfo);
    }

    @Override
    public EntityFormInfo generateEntityFormInfo(ClassMetadata classMetadata) {
        EntityInfo classInfo = generateEntityInfo(classMetadata);
        return EntityFormInfoBuilder.build(classInfo);
    }
}
