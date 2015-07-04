package com.taoswork.tallybook.dynamic.dataservice.service.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public final class DynamicEntityServiceImpl implements DynamicEntityService {

    @Resource(name=DynamicEntityDao.COMPONENT_NAME)
    protected DynamicEntityDao dynamicEntityDao;

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    public DynamicEntityServiceImpl(){
    }

    @Override
    public <T> T save(T entity){
        return dynamicEntityDao.persist(entity);
    }

    @Override
    public <T> T find(Class<T> entityClz, Object key){
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return dynamicEntityDao.find(entityRootClz, key);
    }

    @Override
    public <T> T update(T entity){
        return dynamicEntityDao.update(entity);
    }

    @Override
    public <T> void delete(T entity){
        dynamicEntityDao.remove(entity);
    }

    @Override
    public <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query){
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return dynamicEntityDao.query(entityRootClz, query);
    }

    @Override
    public Class<?> getRootInstanceableEntityClass(Class<?> entityClz){
        Class<?> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return entityClz;
    }

    @Override
    public <T> ClassTreeMetadata inspect(Class<T> entityClz){
        return dynamicEntityMetadataAccess.getClassTreeMetadata(entityClz);
    }

    @Override
    public <T> IEntityInfo describe(Class<T> entityType, EntityInfoType infoType) {
        return dynamicEntityMetadataAccess.getEntityInfo(entityType, infoType);
    }
}
