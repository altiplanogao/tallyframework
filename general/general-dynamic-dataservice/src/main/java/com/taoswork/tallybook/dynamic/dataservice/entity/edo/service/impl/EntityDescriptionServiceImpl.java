package com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.EdoBuilder;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import org.apache.commons.collections.map.LRUMap;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class EntityDescriptionServiceImpl implements EntityDescriptionService {
    @Resource(name = EntityMetadataService.SERVICE_NAME)
    private EntityMetadataService entityMetadataService;

    private Map<String, ClassEdo> classDtoCache = new LRUMap();

    public EntityMetadataService getEntityMetadataService() {
        return entityMetadataService;
    }

    public void setEntityMetadataService(EntityMetadataService entityMetadataService) {
        this.entityMetadataService = entityMetadataService;
    }

    @Override
    public ClassEdo getClassEdo(Class<?> clz){
        return getClassEdo(clz.getName());
    }

    @Override
    public ClassEdo createClassEdo(String clzName) {
        EntityMetadataService entityMetadataServiceLocal = entityMetadataService;
        ClassMetadata classMetadata = entityMetadataServiceLocal.getClassMetadata(clzName);
        ClassEdo classEdo = EdoBuilder.buildClassEdo(classMetadata, null, entityMetadataServiceLocal);
        classDtoCache.put(clzName, classEdo);
        return classEdo;
    }

    @Override
    public ClassEdo getClassEdo(String clzName){
        ClassEdo classEdo = classDtoCache.get(clzName);
        if(classEdo == null){
            EntityMetadataService entityMetadataServiceLocal = entityMetadataService;
            ClassMetadata classMetadata = entityMetadataServiceLocal.getClassMetadata(clzName);
            classEdo = EdoBuilder.buildClassEdo(classMetadata, null, entityMetadataServiceLocal);
            classDtoCache.put(clzName, classEdo);
        }
        return classEdo;
    }
}
