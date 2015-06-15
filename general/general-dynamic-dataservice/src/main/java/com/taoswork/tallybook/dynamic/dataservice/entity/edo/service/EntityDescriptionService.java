package com.taoswork.tallybook.dynamic.dataservice.entity.edo.service;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface EntityDescriptionService {
    public static final String SERVICE_NAME = "EntityDescriptionService";

    ClassEdo createClassEdo(String clzName);

    ClassEdo getClassEdo(Class<?> clz);

    ClassEdo getClassEdo(String clzName);
}
