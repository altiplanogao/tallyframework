package com.taoswork.tallybook.dynamic.dataservice.entity.description.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.builder.EntityFormInfoBuilder;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.builder.EntityGridInfoBuilder;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.builder.EntityInfoBuilder;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.general.solution.cache.ehcache.CacheType;
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

    private static ICacheMap<ClassMetadata, EntityInfo> defaultClassInfoCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache, EntityDescriptionService.class.getSimpleName());

    private ICacheMap<ClassMetadata, EntityInfo> entityInfoCache = defaultClassInfoCache;
//    private Map<ClassMetadata, EntityInfo> entityInfoCache = new LRUMap();


    @Override
    public String getCacheScope() {
        return entityInfoCache.getScopeName();
    }

    @Override
    public void setCacheScope(String scope) {
        entityInfoCache = CachedRepoManager.getCacheMap(CacheType.EhcacheCache, scope);
    }

    public EntityMetadataService getEntityMetadataService() {
        return entityMetadataService;
    }

    public void setEntityMetadataService(EntityMetadataService entityMetadataService) {
        this.entityMetadataService = entityMetadataService;
    }

    private EntityInfo createClassInfo(ClassMetadata classMetadata) {
        EntityInfo entityInfo = EntityInfoBuilder.buildClassInfo(classMetadata, null, entityMetadataService);
        entityInfoCache.put(classMetadata, entityInfo);
        return entityInfo;
    }

    @Override
    public ClassMetadata createClassMetadata(Class clazz) {
        ClassMetadata classMetadata = entityMetadataService.getClassMetadata(clazz);
        return classMetadata;
    }

    @Override
    public EntityInfo getEntityInfo(ClassMetadata classMetadata){
        EntityInfo entityInfo = entityInfoCache.get(classMetadata);
        if(entityInfo == null){
            entityInfo = createClassInfo(classMetadata);
            entityInfoCache.put(classMetadata, entityInfo);
        }
        return entityInfo;
    }

    @Override
    public EntityGridInfo getEntityGridInfo(ClassMetadata classMetadata) {
        EntityInfo classInfo = getEntityInfo(classMetadata);
        return EntityGridInfoBuilder.build(classInfo);
    }

    @Override
    public EntityFormInfo getEntityFormInfo(ClassMetadata classMetadata) {
        EntityInfo classInfo = getEntityInfo(classMetadata);
        return EntityFormInfoBuilder.build(classInfo);
    }
}
