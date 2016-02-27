package com.taoswork.tallybook.dataservice.jpa.core.metaaccess;

import com.taoswork.tallybook.dataservice.core.metaaccess.helper.EntityMetaRawAccess;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import com.taoswork.tallybook.general.solution.time.IntervalSensitive;
import org.apache.commons.collections4.map.LRUMap;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/21.
 */

@ThreadSafe
public class JpaEntityMetaRawAccess implements EntityMetaRawAccess {
    public JpaEntityMetaRawAccess() {
        Long cacheTtl = RuntimePropertiesPublisher.instance().getLong("cache.entity.dao.metadata.ttl", -1L);
        cacheDecider = new IntervalSensitive(cacheTtl) {
            @Override
            protected void onExpireOccur() {
                clearCache();
            }
        };
    }

    public static final Object lock = new Object();
    @GuardedBy("lock")
    public static final Map<Class<?>, Class<?>[]> ENTITY_CACHE = new LRUMap(100, 1000);
    @GuardedBy("lock")
    public static final Map<Class<?>, Class<?>[]> ENTITY_CACHE_FOR_INSTANTIABLE = new LRUMap(100, 1000);
    protected final IntervalSensitive cacheDecider;

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Class<?>[] getAllEntities() {
        Metamodel mm = entityManager.getMetamodel();
        Set<EntityType<?>> entityTypes = mm.getEntities();

        List<Class<?>> entityClasses = new ArrayList<Class<?>>();
        for (EntityType<?> entityType : entityTypes) {
            Class<?> mappedClz = entityType.getJavaType();
            if (mappedClz != null) {
                entityClasses.add(mappedClz);
            }
        }
        return entityClasses.toArray(new Class[]{});
    }
//
//    @Override
//    public Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz, boolean includeNotInstantiable) {
//        return getAllEntitiesFromCeiling(ceilingClz,
//            includeNotInstantiable, !cacheDecider.checkExpired());
//    }
//
//    private Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz,
//                                                 boolean includeNotInstantiable,
//                                                 boolean useCache) {
//        Metamodel mm = entityManager.getMetamodel();
//        Set<EntityType<?>> entityTypes = mm.getEntities();
//        Class<?>[] cache = null;
//        synchronized (lock) {
//            if (useCache) {
//                if (includeNotInstantiable) {
//                    cache = ENTITY_CACHE.get(ceilingClz);
//                } else {
//                    cache = ENTITY_CACHE_FOR_INSTANTIABLE.get(ceilingClz);
//                }
//            }
//            if (cache == null) {
//                List<Class<?>> entities = new ArrayList<Class<?>>();
//                for (EntityType et : entityTypes) {
//                    Class<?> mappedClass = et.getJavaType();
//                    if (mappedClass != null && ceilingClz.isAssignableFrom(mappedClass)) {
//                        entities.add(mappedClass);
//                    }
//                }
//                Class<?>[] sortedEntities = sortEntities(ceilingClz, entities);
//
//                List<Class<?>> filteredSortedEntities = new ArrayList<Class<?>>();
//
//                for (int i = 0; i < sortedEntities.length; i++) {
//                    Class<?> item = sortedEntities[i];
//                    if (includeNotInstantiable) {
//                        filteredSortedEntities.add(sortedEntities[i]);
//                    } else {
//                        if (NativeClassHelper.isInstantiable(item)) {
//                            filteredSortedEntities.add(sortedEntities[i]);
//                        } else {
//                            continue;
//                        }
//                    }
//                }
//
//                Class<?>[] filteredEntities = new Class<?>[filteredSortedEntities.size()];
//                filteredEntities = filteredSortedEntities.toArray(filteredEntities);
//                cache = filteredEntities;
//                if (includeNotInstantiable) {
//                    ENTITY_CACHE.put(ceilingClz, filteredEntities);
//                } else {
//                    ENTITY_CACHE_FOR_INSTANTIABLE.put(ceilingClz, filteredEntities);
//                }
//            }
//        }
//
//        return cache;
//    }

    @Override
    public Field getIdField(Class<?> entityClass) {
        Metamodel mm = entityManager.getMetamodel();
        EntityType<?> entityType = mm.entity(entityClass);
        if (entityType == null) {
            return null;
        }

        Class idType = entityType.getIdType().getJavaType();
        SingularAttribute idAttri = entityType.getId(idType);
        try {
            String idFieldName = idAttri.getName();
            Class declaringType = idAttri.getDeclaringType().getJavaType(); //NOTE, may not equal to @param entityClass
            Field idField = declaringType.getDeclaredField(idFieldName);
            return idField;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearCache() {
        synchronized (lock) {
            ENTITY_CACHE.clear();
            ENTITY_CACHE_FOR_INSTANTIABLE.clear();
        }
    }
}
