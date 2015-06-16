package com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.EdoBuilder;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.cache.ehcache.HasCacheScope;
import com.taoswork.tallybook.general.solution.cache.ehcache.ICacheMap;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class EntityDescriptionServiceImpl implements
        EntityDescriptionService,
        HasCacheScope {

    @Resource(name = EntityMetadataService.SERVICE_NAME)
    private EntityMetadataService entityMetadataService;

    private static ICacheMap<ClassMetadata, ClassEdo> defaultClassEdoCache =
            CachedRepoManager.getCacheMap(EntityDescriptionService.class.getSimpleName());

    private ICacheMap<ClassMetadata, ClassEdo> classEdoCache = defaultClassEdoCache;
//    private Map<ClassMetadata, ClassEdo> classEdoCache = new LRUMap();


    @Override
    public String getCacheScope() {
        return classEdoCache.getScopeName();
    }

    @Override
    public void setCacheScope(String scope) {
        classEdoCache = CachedRepoManager.getCacheMap(scope);
    }

    public EntityMetadataService getEntityMetadataService() {
        return entityMetadataService;
    }

    public void setEntityMetadataService(EntityMetadataService entityMetadataService) {
        this.entityMetadataService = entityMetadataService;
    }

    private ClassEdo createClassEdo(ClassMetadata classMetadata) {
        ClassEdo classEdo = EdoBuilder.buildClassEdo(classMetadata, null, entityMetadataService);
        classEdoCache.put(classMetadata, classEdo);
        return classEdo;
    }

    private ClassEdo createClassEdo(String clzName) {
        ClassMetadata classMetadata = entityMetadataService.getClassMetadata(clzName);
        return createClassEdo(classMetadata);
    }

    @Override
    public ClassEdo getClassEdo(ClassMetadata classMetadata){
        ClassEdo classEdo = classEdoCache.get(classMetadata);
        if(classEdo == null){
            classEdo = createClassEdo(classMetadata);
            classEdoCache.put(classMetadata, classEdo);
        }
        return classEdo;
    }

    @Override
    public ClassEdo getClassEdo(Class<?> clz){
        return getClassEdo(clz.getName());
    }

    @Override
    public ClassEdo getClassEdo(String clzName){
        ClassMetadata classMetadata = entityMetadataService.getClassMetadata(clzName);
        return getClassEdo(classMetadata);
    }
}
