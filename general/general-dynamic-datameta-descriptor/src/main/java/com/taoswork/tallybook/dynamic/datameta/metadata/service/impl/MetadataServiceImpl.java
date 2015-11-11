package com.taoswork.tallybook.dynamic.datameta.metadata.service.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.ClassMetadataUtils;
import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.ImmutableClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.MutableClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.cache.ehcache.CacheType;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.cache.ehcache.ICacheMap;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
@ThreadSafe
public class MetadataServiceImpl implements MetadataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataService.class);

    protected final ClassProcessor classProcessor;

    private static boolean useCache = true;
    //Just cache for Class (without super metadata) , not for EntityClassTree
    @GuardedBy("lock")
    private ICacheMap<String, MutableClassMetadata> classMetadataCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache);
    private Object lock = new Object();

    public MetadataServiceImpl(){
        classProcessor = new ClassProcessor();
    }

    @Override
    public IClassMetadata generateMetadata(final EntityClassTree entityClassTree, String idFieldName, boolean includeSuper) {
        final ClassTreeMetadata classTreeMetadata = new ClassTreeMetadata(entityClassTree);
        //Handle the fields in current class
        {
            final Class rootClz = entityClassTree.getData().clz;
            classProcessor.process(rootClz, classTreeMetadata);
            if (includeSuper) {
                innerAbsorbSuper(rootClz, classTreeMetadata, idFieldName);
            }
        }

        //Thread confinement object
        final List<Class> classesInTree = new ArrayList<Class>();
        entityClassTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                Class clz = parameter.clz;
                if (!clz.isInterface()) {
                    classesInTree.add(clz);
                }
                return null;
            }
        }, false);

        final Set<Class> classesIncludingSuper = new HashSet<Class>();
        for (Class clz : classesInTree) {
            Class[] superClasses = NativeClassHelper.getSuperClasses(clz, true);
            classesIncludingSuper.add(clz);
            for (Class spClz : superClasses) {
                classesIncludingSuper.add(spClz);
            }
        }
        classesIncludingSuper.remove(Object.class);

        for (Class clz : classesIncludingSuper) {
            IClassMetadata classMetadata = generateMetadata(clz, idFieldName);
            classTreeMetadata.absorb(classMetadata);
        }

        publishReferencedEntityMetadataIfNot(classTreeMetadata, idFieldName);
        ImmutableClassMetadata immutableClassMetadata = new ImmutableClassMetadata(classTreeMetadata);
        return immutableClassMetadata;
    }

    @Override
    public IClassMetadata generateMetadata(Class clz, String idFieldName) {
        return generateMetadata(clz, idFieldName, false);
    }

    @Override
    public IClassMetadata generateMetadata(Class clz, String idFieldName, boolean includeSuper) {
        MutableClassMetadata mutableClassMetadata = innerGenerateMetadata(clz, idFieldName, includeSuper);
        publishReferencedEntityMetadataIfNot(mutableClassMetadata, idFieldName);

        ImmutableClassMetadata immutableClassMetadata= new ImmutableClassMetadata(mutableClassMetadata);
        return immutableClassMetadata;
    }

    @Override
    public boolean isMetadataCached(Class clz) {
        return classMetadataCache.containsKey(clz.getName());
    }

    @Override
    public void clearCache() {
        classMetadataCache.clear();
    }

    private MutableClassMetadata innerGenerateMetadata(Class clz, String idFieldName, boolean includeSuper) {
        if(!includeSuper){
            return innerGenerateMetadata(clz, idFieldName);
        }
        MutableClassMetadata mergedMetadata = innerGenerateMetadata(clz, idFieldName).clone();
        innerAbsorbSuper(clz, mergedMetadata, idFieldName);

        return mergedMetadata;
    }

    private MutableClassMetadata innerGenerateMetadata(Class clz, String idFieldName) {
        String clzName = clz.getName();
        MutableClassMetadata mutableClassMetadata = null;

        if(!useCache){
            mutableClassMetadata = doInnerGenerateMetadata(clz, idFieldName);
            return mutableClassMetadata;
        }

        synchronized (lock) {
            mutableClassMetadata = classMetadataCache.get(clzName);
            if (null == mutableClassMetadata) {
                mutableClassMetadata = doInnerGenerateMetadata(clz, idFieldName);
            }
            return mutableClassMetadata;
        }
    }

    private MutableClassMetadata doInnerGenerateMetadata(Class clz, String idFieldName) {
        String clzName = clz.getName();
        MutableClassMetadata mutableClassMetadata = new MutableClassMetadata(clz);

        classProcessor.process(clz, mutableClassMetadata);

        try {
            if (StringUtils.isNotEmpty(idFieldName)) {
                Field idField = null;
                idField = clz.getField(idFieldName);
                mutableClassMetadata.setIdFieldIfNone(idField);
            }
        } catch (NoSuchFieldException e) {
            //ignore this idfield
        }
        classMetadataCache.put(clzName, mutableClassMetadata);
        return mutableClassMetadata;
    }

    private void innerAbsorbSuper(Class clz, MutableClassMetadata mergedMetadata, String idFieldName) {
        final List<IClassMetadata> tobeMerged = new ArrayList<IClassMetadata>();

        Class[] superClasses = NativeClassHelper.getSuperClasses(clz, false);
        for (Class superClz : superClasses) {
            IClassMetadata mutableClassMetadata = innerGenerateMetadata(superClz, idFieldName);
            tobeMerged.add(mutableClassMetadata);
        }

        for(IClassMetadata classMetadata : tobeMerged){
            mergedMetadata.absorbSuper(classMetadata);
        }
    }

    private void publishReferencedEntityMetadataIfNot(MutableClassMetadata mutableClassMetadata, String idFieldName){
        if(!mutableClassMetadata.isReferencingClassMetadataPublished()){
            Collection<Class> entities = ClassMetadataUtils.calcReferencedTypes(mutableClassMetadata);
            Set<IClassMetadata> mutableClassMetadatas = new HashSet<IClassMetadata>();
            for (Class entity : entities){
                IClassMetadata metadata = this.innerGenerateMetadata(entity, idFieldName, true);
                mutableClassMetadatas.add(metadata);
            }
            mutableClassMetadata.publishReferencingClassMetadatas(mutableClassMetadatas);
        }
    }
}
