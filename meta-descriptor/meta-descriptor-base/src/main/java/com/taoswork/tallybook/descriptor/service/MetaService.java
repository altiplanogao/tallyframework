package com.taoswork.tallybook.descriptor.service;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaService {
    public static final String SERVICE_NAME = "MetadataAnalyzeService";

    IClassMeta generateMeta(EntityClassTree entityClassTree, String idFieldName, boolean includeSuper);

    IClassMeta generateMeta(Class clz, String idFieldName);

    IClassMeta generateMeta(Class clz, String idFieldName, boolean handleSuper);

    boolean isMetadataCached(Class clz);

    void clearCache();
}
