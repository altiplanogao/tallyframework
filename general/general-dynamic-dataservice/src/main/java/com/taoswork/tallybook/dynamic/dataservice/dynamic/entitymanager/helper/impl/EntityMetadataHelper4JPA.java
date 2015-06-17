package com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.helper.impl;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.helper.EntityMetadataHelper;
import com.taoswork.tallybook.general.solution.cache.CacheDecider;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
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
public class EntityMetadataHelper4JPA extends EntityMetadataHelper {

    public static final Object LOCK_OBJECT = new Object();
    public static final Map<Class<?>, Class<?>[]> POLYMORPHIC_ENTITY_CACHE = new LRUMap(100, 1000);
    public static final Map<Class<?>, Class<?>[]> POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS = new LRUMap(100, 1000);

    private EntityManager entityManager;

    protected CacheDecider cacheDecider;

    private CacheDecider getCacheDecider(){
        if (cacheDecider == null){
            Long cacheTtl = RuntimePropertiesPublisher.instance().getLong("cache.entity.dao.metadata.ttl", -1L);
            cacheDecider = new CacheDecider(cacheTtl) {
                @Override
                protected void clearCache() {
                    clearCache();
                }
            };
        }
        return cacheDecider;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClz) {
        return getAllPolymorphicEntitiesFromCeiling(ceilingClz, true);
    }

    @Override
    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClz, boolean includeUnqualifiedPolymorphicEntities) {
        return getAllPolymorphicEntitiesFromCeiling(ceilingClz,
                includeUnqualifiedPolymorphicEntities, getCacheDecider().useCache());
    }

    private Class<?>[] getAllPolymorphicEntitiesFromCeiling(
            Class<?> ceilingClz,
            boolean includeUnqualifiedPolymorphicEntities,
            boolean useCache) {
        Metamodel mm = entityManager.getMetamodel();
        Set<EntityType<?>> entityTypes = mm.getEntities();
        Class<?>[] cache = null;
        synchronized(LOCK_OBJECT) {
            if (useCache) {
                if (includeUnqualifiedPolymorphicEntities) {
                    cache = POLYMORPHIC_ENTITY_CACHE.get(ceilingClz);
                } else {
                    cache = POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.get(ceilingClz);
                }
            }
            if (cache == null) {
                List<Class<?>> entities = new ArrayList<Class<?>>();
                for (EntityType et : entityTypes) {
                    Class<?> mappedClass = et.getBindableJavaType();
                    if (mappedClass != null && ceilingClz.isAssignableFrom(mappedClass)) {
                        entities.add(mappedClass);
                    }
                }
                Class<?>[] sortedEntities = sortEntities(ceilingClz, entities);

                List<Class<?>> filteredSortedEntities = new ArrayList<Class<?>>();

                for (int i = 0; i < sortedEntities.length; i++) {
                    Class<?> item = sortedEntities[i];
                    if (includeUnqualifiedPolymorphicEntities) {
                        filteredSortedEntities.add(sortedEntities[i]);
                    } else {
                        if (isExcludeClassFromPolymorphism(item)) {
                            continue;
                        } else {
                            filteredSortedEntities.add(sortedEntities[i]);
                        }
                    }
                }

                Class<?>[] filteredEntities = new Class<?>[filteredSortedEntities.size()];
                filteredEntities = filteredSortedEntities.toArray(filteredEntities);
                cache = filteredEntities;
                if (includeUnqualifiedPolymorphicEntities) {
                    POLYMORPHIC_ENTITY_CACHE.put(ceilingClz, filteredEntities);
                } else {
                    POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.put(ceilingClz, filteredEntities);
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
        for(Attribute attribute : entityType.getAttributes()){
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

    private void clearCache(){
        POLYMORPHIC_ENTITY_CACHE.clear();
        POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.clear();
    }

    @Override
    public Class<?>[] getAllEntityClasses() {
        Metamodel mm = entityManager.getMetamodel();
        Set<EntityType<?>> entityTypes = mm.getEntities();

        List<Class<?>> entityClasses = new ArrayList<Class<?>>();
        for (EntityType<?> entityType : entityTypes){
            Class<?> mappedClz = entityType.getJavaType();
            if(mappedClz != null){
                entityClasses.add(mappedClz);
            }
        }
        return entityClasses.toArray(new Class[]{});
    }
}
