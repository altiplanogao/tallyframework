package com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.helper.impl;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.helper.EntityMetadataHelper;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.solution.time.IntervalSensitive;
import org.apache.commons.collections.map.LRUMap;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public class EntityMetadataHelper4Hibernate extends EntityMetadataHelper {

    public static final Object LOCK_OBJECT = new Object();
    public static final Map<Class<?>, Class<?>[]> POLYMORPHIC_ENTITY_CACHE = new LRUMap(100, 1000);
    public static final Map<Class<?>, Class<?>[]> POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS = new LRUMap(100, 1000);

    private HibernateEntityManager hibernateEntityManager;

    protected IntervalSensitive cacheDecider;

    private IntervalSensitive getCacheDecider(){
        if (cacheDecider == null){
            Long cacheTtl = RuntimePropertiesPublisher.instance().getLong("cache.entity.dao.metadata.ttl", -1L);
            cacheDecider = new IntervalSensitive(cacheTtl) {
                @Override
                protected void onExpireOccur() {
                    clearCache();
                }
            };
        }
        return cacheDecider;
    }

    @Override
    public HibernateEntityManager getEntityManager() {
        return hibernateEntityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.hibernateEntityManager = (HibernateEntityManager)entityManager;
    }

    @Override
    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClz) {
        return getAllPolymorphicEntitiesFromCeiling(ceilingClz, true);
    }

    @Override
    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClz, boolean includeUnqualifiedPolymorphicEntities) {
        return getAllPolymorphicEntitiesFromCeiling(ceilingClz,
                includeUnqualifiedPolymorphicEntities, getCacheDecider().isIntervalExpired());
    }

    private Class<?>[] getAllPolymorphicEntitiesFromCeiling(
            Class<?> ceilingClz,
            boolean includeUnqualifiedPolymorphicEntities,
            boolean useCache) {
        SessionFactory sessionFactory = this.getSessionFactory();
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
                for (Object item : sessionFactory.getAllClassMetadata().values()) {
                    ClassMetadata metadata = (ClassMetadata) item;
                    Class<?> mappedClass = metadata.getMappedClass();
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
        SessionFactory sessionFactory = getSessionFactory();

        ClassMetadata metadata = sessionFactory.getClassMetadata(entityClz);
        if (metadata == null) {
            return null;
        }

        String idProperty = metadata.getIdentifierPropertyName();
        response.put("name", idProperty);
        Type idType = metadata.getIdentifierType();
        response.put("type", idType.getName());

        return response;
    }

    @Override
    public List<String> getPropertyNames(Class<?> entityClz) {
        ClassMetadata metadata = getSessionFactory().getClassMetadata(entityClz);
        List<String> propertyNames = new ArrayList<String>();
        Collections.addAll(propertyNames, metadata.getPropertyNames());
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

    private SessionFactory getSessionFactory() {
        return hibernateEntityManager.getSession().getSessionFactory();
    }

    @Override
    public Class<?>[] getAllEntityClasses() {
        List<Class<?>> entityClasses = new ArrayList<Class<?>>();
        SessionFactory sessionFactory = this.getSessionFactory();
        for (ClassMetadata classMetadata : sessionFactory.getAllClassMetadata().values()){
            Class<?> mappedClz = classMetadata.getMappedClass();
            if(mappedClz != null){
                entityClasses.add(mappedClz);
            }
        }
        return entityClasses.toArray(new Class[]{});
    }
}
