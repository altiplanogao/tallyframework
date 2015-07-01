package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaDescriptionService {
    public static final String SERVICE_NAME = "MetaDescriptionService";

    ClassMetadata createClassMetadata(Class clazz);

    EntityInfo getEntityInfo(ClassMetadata classMetadata);

    EntityGridInfo getEntityGridInfo(ClassMetadata classMetadata);

    EntityFormInfo getEntityFormInfo(ClassMetadata classMetadata);

}
