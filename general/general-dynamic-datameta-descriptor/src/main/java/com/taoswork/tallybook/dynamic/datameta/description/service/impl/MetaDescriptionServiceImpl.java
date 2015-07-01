package com.taoswork.tallybook.dynamic.datameta.description.service.impl;

import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityFormInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityGridInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.builder.EntityInfoBuilder;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.MetaDescriptionService;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.general.solution.cache.ehcache.CacheType;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.cache.ehcache.HasCacheScope;
import com.taoswork.tallybook.general.solution.cache.ehcache.ICacheMap;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaDescriptionServiceImpl implements
        MetaDescriptionService,
        HasCacheScope {

    @Resource(name = MetadataService.SERVICE_NAME)
    private MetadataService metadataService;

    private static ICacheMap<ClassMetadata, EntityInfo> defaultClassInfoCache =
            CachedRepoManager.getCacheMap(CacheType.EhcacheCache, MetaDescriptionService.class.getSimpleName());

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

    public MetadataService getMetadataService() {
        return metadataService;
    }

    public void setMetadataService(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    private EntityInfo createClassInfo(ClassMetadata classMetadata) {
        EntityInfo entityInfo = EntityInfoBuilder.buildClassInfo(classMetadata, null, metadataService);
        entityInfoCache.put(classMetadata, entityInfo);
        return entityInfo;
    }

    @Override
    public ClassMetadata createClassMetadata(Class clazz) {
        ClassMetadata classMetadata = metadataService.getClassMetadata(clazz);
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
