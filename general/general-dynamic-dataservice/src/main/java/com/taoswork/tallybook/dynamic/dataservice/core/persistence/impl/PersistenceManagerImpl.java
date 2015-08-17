package com.taoswork.tallybook.dynamic.dataservice.core.persistence.impl;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerImpl implements PersistenceManager {

    @Resource(name= DynamicEntityDao.COMPONENT_NAME)
    protected DynamicEntityDao dynamicEntityDao;

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Override
    public <T> T persist(T entity){
        return dynamicEntityDao.persist(entity);
    }

    @Override
    public <T> T find(Class<T> entityClz, Object key) {
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
}
