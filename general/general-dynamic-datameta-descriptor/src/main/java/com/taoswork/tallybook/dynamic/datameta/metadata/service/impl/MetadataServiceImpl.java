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
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
@ThreadSafe
public class MetadataServiceImpl implements MetadataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataService.class);

    protected final ClassProcessor classProcessor;
    protected final FieldProcessor fieldProcessor;

    //Just cache for Class, not for EntityClassTree
    @GuardedBy("lock")
    private ICacheMap<String, ClassMetadata> classMetadataCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache);
    private Object lock = new Object();

    public MetadataServiceImpl(){
        fieldProcessor = new FieldProcessor();
        classProcessor = new ClassProcessor(fieldProcessor);
    }

    @Override
    public ClassTreeMetadata generateMetadata(final EntityClassTree entityClassTree) {
        final ClassTreeMetadata classTreeMetadata = new ClassTreeMetadata();
        classTreeMetadata.setEntityClassTree(entityClassTree);
        //Handle the fields in current class
        {
            final Class rootClz = entityClassTree.getData().clz;
            classProcessor.process(rootClz, classTreeMetadata);
        }

        //Thread confinement object
        final List<Class> classesInTree = new ArrayList<Class>();
        entityClassTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                Class clz = parameter.clz;
                if(!clz.isInterface()){
                    classesInTree.add(clz);
                }
                return null;
            }
        }, false);

        final Set<Class> classesWithSuper = new HashSet<Class>();
        for(Class clz : classesInTree){
            Class[] superClasses = NativeClassHelper.getSuperClasses(clz, true);
            classesWithSuper.add(clz);
            for (Class spClz : superClasses){
                classesWithSuper.add(spClz);
            }
        }

        for(Class clz : classesWithSuper){
            ClassMetadata classMetadata = generateMetadata(clz);
            classTreeMetadata.absorb(classMetadata);
        }

        return classTreeMetadata;
    }

    @Override
    public ClassMetadata generateMetadata(Class clz) {
        String clzName = clz.getName();

        synchronized (lock) {
            ClassMetadata classMetadata = classMetadataCache.getOrDefault(clzName, null);
            if (null == classMetadata) {
                classMetadata = new ClassMetadata();

                classProcessor.process(clz, classMetadata);
                //doGenerateClassMetadata(clz, classMetadata);
                classMetadataCache.put(clzName, classMetadata);
            }
            return classMetadata;
        }
    }

    @Override
    public ClassMetadata generateMetadata(Class clz, boolean handleSuper) {
        if(!handleSuper){
            return generateMetadata(clz);
        }
        ClassMetadata mergedMetadata = generateMetadata(clz).clone();
        final List<ClassMetadata> tobeMerged = new ArrayList<ClassMetadata>();

        Class[] superClasses = NativeClassHelper.getSuperClasses(clz, true);
        for (Class superClz : superClasses) {
            ClassMetadata classMetadata = generateMetadata(superClz);
            tobeMerged.add(classMetadata);
        }

        for(ClassMetadata classMetadata : tobeMerged){
            mergedMetadata.absorbSuper(classMetadata);
        }

        return mergedMetadata;
    }

    //    private void doGenerateClassMetadata(Class<?> clz, ClassMetadata classMetadata){
//        classProcessor.process(clz, classMetadata);
//    }

}
