package com.taoswork.tallybook.dynamic.dataservice.dynamic.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaTransferObject;

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
    public Class<?> getRootPersistiveEntityClass(Class<?> entityClz){
        Class<?> entityRootClz = this.dynamicEntityMetadataAccess.getRootPersistiveEntityClass(entityClz);
        return entityClz;
    }

    @Override
    public <T> T save(T entity){
        return dynamicEntityDao.persist(entity);
    }

    @Override
    public <T> T find(Class<T> entityClz, Object key){
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootPersistiveEntityClass(entityClz);
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
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootPersistiveEntityClass(entityClz);
        return dynamicEntityDao.query(entityRootClz, query);
    }

    @Override
    public <T> ClassTreeMetadata inspect(Class<T> entityClz){
        return dynamicEntityMetadataAccess.getClassTreeMetadata(entityClz);
    }
}
