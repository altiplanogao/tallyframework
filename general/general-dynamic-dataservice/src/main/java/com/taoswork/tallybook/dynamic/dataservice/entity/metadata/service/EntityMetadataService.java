package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface EntityMetadataService {
    public static final String SERVICE_NAME = "EntityMetadataService";

    ClassMetadata getClassMetadata(Class clz);

    ClassMetadata getClassMetadata(String clzName);

    FieldMetadata getFieldMetadata(Field field);
}
