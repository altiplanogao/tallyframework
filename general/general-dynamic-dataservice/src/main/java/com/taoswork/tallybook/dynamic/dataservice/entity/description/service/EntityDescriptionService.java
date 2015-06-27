package com.taoswork.tallybook.dynamic.dataservice.entity.description.service;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface EntityDescriptionService {
    public static final String SERVICE_NAME = "EntityDescriptionService";

    ClassMetadata createClassMetadata(Class clazz);

    EntityInfo getEntityInfo(ClassMetadata classMetadata);

    EntityGridInfo getEntityGridInfo(ClassMetadata classMetadata);

    EntityFormInfo getEntityFormInfo(ClassMetadata classMetadata);

}
