package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetadataAnalyzeService {
    public static final String SERVICE_NAME = "MetadataAnalyzeService";

    ClassTreeMetadata workOutMetadata(EntityClassTree entityClassTree);

    ClassMetadata workOutMetadata(Class clz);
}