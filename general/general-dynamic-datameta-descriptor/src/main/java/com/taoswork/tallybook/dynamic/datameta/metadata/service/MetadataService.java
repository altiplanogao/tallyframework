package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetadataService {
    public static final String SERVICE_NAME = "MetadataAnalyzeService";

    IClassMetadata generateMetadata(EntityClassTree entityClassTree, String idFieldName, boolean includeSuper);

    IClassMetadata generateMetadata(Class clz, String idFieldName);

    IClassMetadata generateMetadata(Class clz, String idFieldName, boolean handleSuper);

    boolean isMetadataCached(Class clz);

    void clearCache();
}
