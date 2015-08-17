package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper.AEntityMetadataRawAccess;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import com.taoswork.tallybook.general.solution.time.IntervalSensitive;
import org.apache.commons.collections.map.LRUMap;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.Type;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/21.
 */

@ThreadSafe
public class EntityMetadataRawAccessJPA extends AEntityMetadataRawAccess {
    public EntityMetadataRawAccessJPA(){
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
    public static final Map<Class<?>, Class<?>[]> ENTITY_CACHE_FOR_INSTANCEABLE = new LRUMap(100, 1000);
    protected final IntervalSensitive cacheDecider;

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz) {
        return getAllEntitiesFromCeiling(ceilingClz, true);
    }

    @Override
    public Class<?>[] getAllEntitiesFromCeiling(Class<?> ceilingClz, boolean includeNotInstanceable) {
        return getAllEntitiesFromCeiling(ceilingClz,
            includeNotInstanceable, !cacheDecider.isIntervalExpired());
    }

    private Class<?>[] getAllEntitiesFromCeiling(
        Class<?> ceilingClz,
        boolean includeNotInstanceable,
        boolean useCache) {
        Metamodel mm = entityManager.getMetamodel();
        Set<EntityType<?>> entityTypes = mm.getEntities();
        Class<?>[] cache = null;
        synchronized (lock) {
            if (useCache) {
                if (includeNotInstanceable) {
                    cache = ENTITY_CACHE.get(ceilingClz);
                } else {
                    cache = ENTITY_CACHE_FOR_INSTANCEABLE.get(ceilingClz);
                }
            }
            if (cache == null) {
                List<Class<?>> entities = new ArrayList<Class<?>>();
                for (EntityType et : entityTypes) {
                    Class<?> mappedClass = et.getJavaType();
                    if (mappedClass != null && ceilingClz.isAssignableFrom(mappedClass)) {
                        entities.add(mappedClass);
                    }
                }
                Class<?>[] sortedEntities = sortEntities(ceilingClz, entities);

                List<Class<?>> filteredSortedEntities = new ArrayList<Class<?>>();

                for (int i = 0; i < sortedEntities.length; i++) {
                    Class<?> item = sortedEntities[i];
                    if (includeNotInstanceable) {
                        filteredSortedEntities.add(sortedEntities[i]);
                    } else {
                        if (NativeClassHelper.isInstanceable(item)) {
                            filteredSortedEntities.add(sortedEntities[i]);
                        } else {
                            continue;
                        }
                    }
                }

                Class<?>[] filteredEntities = new Class<?>[filteredSortedEntities.size()];
                filteredEntities = filteredSortedEntities.toArray(filteredEntities);
                cache = filteredEntities;
                if (includeNotInstanceable) {
                    ENTITY_CACHE.put(ceilingClz, filteredEntities);
                } else {
                    ENTITY_CACHE_FOR_INSTANCEABLE.put(ceilingClz, filteredEntities);
                }
            }
        }

        return cache;
    }

    @Override
    public Map<String, String> getIdMetadata(Class<?> entityClz) {
        Map<String, String> response = new HashMap<String, String>();
        Metamodel mm = entityManager.getMetamodel();
        EntityType<?> entityType = mm.entity(entityClz);
        if (entityType == null) {
            return null;
        }

        Type<?> idType = entityType.getIdType();
//        idType.
//        String idProperty = metadata.getIdentifierPropertyName();
//        response.put("name", idProperty);
//        Type idType = metadata.getIdentifierType();
//        response.put("type", idType.getName());

        return response;
    }

    @Override
    public List<String> getPropertyNames(Class<?> entityClz) {
        Metamodel mm = entityManager.getMetamodel();
        EntityType<?> entityType = mm.entity(entityClz);
        List<String> propertyNames = new ArrayList<String>();
        for (Attribute attribute : entityType.getAttributes()) {
            propertyNames.add(attribute.getName());
        }
        return propertyNames;
    }

//    @Override
//    public List<Type> getPropertyTypes(Class<?> entityClz) {
//        ClassMetadata metadata = getSessionFactory().getClassMetadata(entityClz);
//        List<Type> propertyTypes = new ArrayList<Type>();
//        Collections.addAll(propertyTypes, metadata.getPropertyTypes());
//        return propertyTypes;
//    }

    private void clearCache() {
        synchronized (lock) {
            ENTITY_CACHE.clear();
            ENTITY_CACHE_FOR_INSTANCEABLE.clear();
        }
    }

    @Override
    public Class<?>[] getAllEntityClasses() {
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
}
