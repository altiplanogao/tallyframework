package com.taoswork.tallybook.dynamic.datameta.metadata.service.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.FieldProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.cache.ehcache.CacheType;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.cache.ehcache.ICacheMap;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceImpl implements MetadataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataService.class);

    protected final ClassProcessor classProcessor;
    protected final FieldProcessor fieldProcessor;

    //Just cache for Class, not for EntityClassTree
    private ICacheMap<String, ClassMetadata> classMetadataCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache);;

    public MetadataServiceImpl(){
        fieldProcessor = new FieldProcessor();
        classProcessor = new ClassProcessor(fieldProcessor);
    }

    @Override
    public ClassTreeMetadata generateMetadata(final EntityClassTree entityClassTree, boolean handleSuper) {
        final ClassTreeMetadata classTreeMetadata = new ClassTreeMetadata();
        classTreeMetadata.setEntityClassTree(entityClassTree);
        final Class rootClz = entityClassTree.getData().clz;

        //Handle the fields in current class
        {
            classProcessor.process(rootClz, classTreeMetadata);
        }

        final List<ClassMetadata> metadatasTobeMerged = new ArrayList<ClassMetadata>();

        //Handle the fields in super classes
        if(handleSuper) {
            Class[] superClasses = NativeClassHelper.getSuperClasses(rootClz, true);
            for (Class superClz : superClasses) {
                ClassMetadata classMetadata = generateMetadata(superClz);
                metadatasTobeMerged.add(classMetadata);
            }
        }

        //Handle the fields in polymorphic children classes
        entityClassTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                Class clz = parameter.clz;
                ClassMetadata classMetadata = generateMetadata(clz);
                metadatasTobeMerged.add(classMetadata);

                if (clz.equals(rootClz)) {
                    LOGGER.error("{} should not be processed here", rootClz.getName());
                    throw new RuntimeException(rootClz.getName() + "should not be processed here.");
                }

                return null;
            }
        }, false);

        for(ClassMetadata classMetadata : metadatasTobeMerged){
            classTreeMetadata.getRWTabMetadataMap().putAll(classMetadata.getReadonlyTabMetadataMap());
            classTreeMetadata.getRWGroupMetadataMap().putAll(classMetadata.getReadonlyGroupMetadataMap());
            classTreeMetadata.getRWFieldMetadataMap().putAll(classMetadata.getReadonlyFieldMetadataMap());
        }

        return classTreeMetadata;
    }

    @Override
    public ClassMetadata generateMetadata(Class clz) {
        String clzName = clz.getName();
        ClassMetadata classMetadata = classMetadataCache.getOrDefault(clzName, null);
        if(null == classMetadata) {
            classMetadata = new ClassMetadata();
            classProcessor.process(clz, classMetadata);
            //doGenerateClassMetadata(clz, classMetadata);
            classMetadataCache.put(clzName, classMetadata);
        }
        return classMetadata;
    }

//    private void doGenerateClassMetadata(Class<?> clz, ClassMetadata classMetadata){
//        classProcessor.process(clz, classMetadata);
//    }

}
