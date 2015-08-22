package com.taoswork.tallybook.dynamic.dataservice.core.persistence.impl;

import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.authority.core.basic.Access;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerImpl implements PersistenceManager {

    @Resource(name= DynamicEntityDao.COMPONENT_NAME)
    protected DynamicEntityDao dynamicEntityDao;

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    @Override
    public <T> T persist(T entity){
        securityVerifier.checkAccess(entity.getClass().getName(), Access.Read, entity);
        return dynamicEntityDao.persist(entity);
    }

    @Override
    public <T> T find(Class<T> entityClz, Object key) {
        securityVerifier.checkAccess(entityClz.getName(), Access.Read);
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return dynamicEntityDao.find(entityRootClz, key);
    }

    @Override
    public <T> T update(T entity){
        securityVerifier.checkAccess(entity.getClass().getName(), Access.Update, entity);
        return dynamicEntityDao.update(entity);
    }

    @Override
    public <T> void delete(T entity){
        securityVerifier.checkAccess(entity.getClass().getName(), Access.Delete, entity);
        dynamicEntityDao.remove(entity);
    }

    @Override
    public <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query){
        securityVerifier.checkAccess(entityClz.getName(), Access.Query);
        Class<T> entityRootClz = this.dynamicEntityMetadataAccess.getRootInstanceableEntityClass(entityClz);
        return dynamicEntityDao.query(entityRootClz, query);
    }
}
