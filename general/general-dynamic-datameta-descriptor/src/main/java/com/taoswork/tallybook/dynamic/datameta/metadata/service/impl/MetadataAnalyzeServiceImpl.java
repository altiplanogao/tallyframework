package com.taoswork.tallybook.dynamic.datameta.metadata.service.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.FieldProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataAnalyzeService;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.cache.ehcache.CacheType;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.cache.ehcache.ICacheMap;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataAnalyzeServiceImpl implements MetadataAnalyzeService {

    protected final ClassProcessor classProcessor;
    protected final FieldProcessor fieldProcessor;

    //Just cache for Class, not for EntityClassTree
    private ICacheMap<String, ClassMetadata> classMetadataCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache);;

    public MetadataAnalyzeServiceImpl(){
        fieldProcessor = new FieldProcessor();
        classProcessor = new ClassProcessor(fieldProcessor);
    }

    @Override
    public ClassTreeMetadata workOutMetadata(final EntityClassTree entityClassTree) {
        final ClassTreeMetadata classTreeMetadata = new ClassTreeMetadata();
        classTreeMetadata.setEntityClassTree(entityClassTree);
        generateClassMetadata(entityClassTree.getData().clz, classTreeMetadata);

        entityClassTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                Class clz = parameter.clz;
                ClassMetadata classMetadata = workOutMetadata(clz);
                if (clz.equals(entityClassTree.getData().clz)) {
                    classTreeMetadata.copyFrom(classMetadata);
                }
                classTreeMetadata.getRWTabMetadataMap().putAll(classMetadata.getReadonlyTabMetadataMap());
                classTreeMetadata.getRWGroupMetadataMap().putAll(classMetadata.getReadonlyGroupMetadataMap());
                classTreeMetadata.getRWFieldMetadataMap().putAll(classMetadata.getReadonlyFieldMetadataMap());
                return null;
            }
        }, false);

        return classTreeMetadata;
    }

    @Override
    public ClassMetadata workOutMetadata(Class clz) {
        String clzName = clz.getName();
        ClassMetadata classMetadata = classMetadataCache.getOrDefault(clzName, null);
        if(null == classMetadata) {
            classMetadata = new ClassMetadata();
            generateClassMetadata(clz, classMetadata);
            classMetadataCache.put(clzName, classMetadata);
        }
        return classMetadata;
    }

    private void generateClassMetadata(Class<?> clz, ClassMetadata classMetadata){
        classProcessor.process(clz, classMetadata);
    }

}
