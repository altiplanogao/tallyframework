package com.taoswork.tallybook.descriptor.service.impl;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ClassMetaUtils;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ClassTreeMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ImmutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClass;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.descriptor.metadata.processor.IClassProcessor;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.cache.ehcache.CacheType;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.cache.ehcache.ICacheMap;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
@ThreadSafe
public class BaseMetaServiceImpl implements MetaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaService.class);

    protected final IClassProcessor classProcessor;

    private static boolean useCache = true;
    //Just cache for Class (without super metadata) , not for EntityClassTree
    @GuardedBy("lock")
    private ICacheMap<String, MutableClassMeta> classMetaCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache);
    private Object lock = new Object();

    public BaseMetaServiceImpl(IClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    @Override
    public IClassMeta generateMeta(final EntityClassTree entityClassTree, String idFieldName, boolean includeSuper) {
        final ClassTreeMeta classTreeMeta = new ClassTreeMeta(entityClassTree);
        classTreeMeta.setContainsSuper(includeSuper);
        //Handle the fields in current class
        {
            final Class rootClz = entityClassTree.getData().clz;
            classProcessor.process(rootClz, classTreeMeta);
            if (includeSuper) {
                innerAbsorbSuper(rootClz, classTreeMeta, idFieldName);
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
            Class[] superClasses = ClassUtility.getSuperClasses(clz, true);
            classesIncludingSuper.add(clz);
            for (Class spClz : superClasses) {
                classesIncludingSuper.add(spClz);
            }
        }
        classesIncludingSuper.remove(Object.class);

        for (Class clz : classesIncludingSuper) {
            IClassMeta classMeta = generateMeta(clz, idFieldName);
            classTreeMeta.absorb(classMeta);
        }

        publishReferencedEntityMetadataIfNot(classTreeMeta, idFieldName);
        ImmutableClassMeta immutableClassMetadata = new ImmutableClassMeta(classTreeMeta);
        return immutableClassMetadata;
    }

    @Override
    public IClassMeta generateMeta(Class clz, String idFieldName) {
        return generateMeta(clz, idFieldName, false);
    }

    @Override
    public IClassMeta generateMeta(Class clz, String idFieldName, boolean includeSuper) {
        MutableClassMeta mutableClassMetadata = innerGenerateMetadata(clz, idFieldName, includeSuper);
        publishReferencedEntityMetadataIfNot(mutableClassMetadata, idFieldName);

        ImmutableClassMeta immutableClassMetadata = new ImmutableClassMeta(mutableClassMetadata);
        return immutableClassMetadata;
    }

    @Override
    public boolean isMetadataCached(Class clz) {
        return classMetaCache.containsKey(clz.getName());
    }

    @Override
    public void clearCache() {
        classMetaCache.clear();
    }

    private MutableClassMeta innerGenerateMetadata(Class clz, String idFieldName, boolean includeSuper) {
        if (!includeSuper) {
            return innerGenerateMetadata(clz, idFieldName);
        }
        MutableClassMeta mergedMetadata = innerGenerateMetadata(clz, idFieldName).clone();
        innerAbsorbSuper(clz, mergedMetadata, idFieldName);

        return mergedMetadata;
    }

    private MutableClassMeta innerGenerateMetadata(Class clz, String idFieldName) {
        String clzName = clz.getName();
        MutableClassMeta mutableClassMetadata = null;

        if (!useCache) {
            mutableClassMetadata = doInnerGenerateMetadata(clz, idFieldName);
            return mutableClassMetadata;
        }

        synchronized (lock) {
            mutableClassMetadata = classMetaCache.get(clzName);
            if (null == mutableClassMetadata) {
                mutableClassMetadata = doInnerGenerateMetadata(clz, idFieldName);
            }
            return mutableClassMetadata;
        }
    }

    private MutableClassMeta doInnerGenerateMetadata(Class clz, String idFieldName) {
        String clzName = clz.getName();
        MutableClassMeta mutableClassMetadata = new MutableClassMeta(clz);

        classProcessor.process(clz, mutableClassMetadata);

        try {
            if (StringUtils.isNotEmpty(idFieldName)) {
                Field idField = null;
                idField = clz.getDeclaredField(idFieldName);
                mutableClassMetadata.setIdFieldIfNone(idField);
            }
        } catch (NoSuchFieldException e) {
            //ignore this idfield
        }
        classMetaCache.put(clzName, mutableClassMetadata);
        return mutableClassMetadata;
    }

    private void innerAbsorbSuper(Class clz, MutableClassMeta mergedMetadata, String idFieldName) {
        final List<IClassMeta> tobeMerged = new ArrayList<IClassMeta>();

        Class[] superClasses = ClassUtility.getSuperClasses(clz, false);
        for (Class superClz : superClasses) {
            IClassMeta mutableClassMetadata = innerGenerateMetadata(superClz, idFieldName);
            tobeMerged.add(mutableClassMetadata);
        }

        for (IClassMeta classMeta : tobeMerged) {
            mergedMetadata.absorbSuper(classMeta);
        }
    }

    private void publishReferencedEntityMetadataIfNot(MutableClassMeta mutableClassMetadata, String idFieldName) {
        if (!mutableClassMetadata.isReferencingClassMetaPublished()) {
            Collection<Class> entities = ClassMetaUtils.calcReferencedTypes(mutableClassMetadata);
            Set<IClassMeta> mutableClassMetadatas = new HashSet<IClassMeta>();
            for (Class entity : entities) {
                IClassMeta metadata = this.innerGenerateMetadata(entity, idFieldName, true);
                mutableClassMetadatas.add(metadata);
            }
            mutableClassMetadata.publishReferencingClassMetadatas(mutableClassMetadatas);
        }
    }
}

