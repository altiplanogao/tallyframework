package com.taoswork.tallybook.dynamic.dataservice.core.dao.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.query.translator.Cto2QueryTranslator;
import com.taoswork.tallybook.dynamic.dataservice.core.query.translator.impl.Cto2QueryTranslatorImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public abstract class DynamicEntityDaoImplBase implements DynamicEntityDao {

    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    protected DynamicEntityMetadataAccess dynamicEntityMetadataAccess;

    private final Cto2QueryTranslator cto2QueryTranslator;
    
    public DynamicEntityDaoImplBase(){
        cto2QueryTranslator = new Cto2QueryTranslatorImpl();
    }

    public abstract EntityManager getEntityManager();
    
    @Override
    public void flush(){
        EntityManager em = getEntityManager();
        em.flush();
    }

    @Override
    public void detach(Object entity){
        EntityManager em = getEntityManager();
        em.detach(entity);
    }

    @Override
    public void refresh(Object entity){
        EntityManager em = getEntityManager();
        em.refresh(entity);
    }

    @Override
    public void clear(){
        EntityManager em = getEntityManager();
        em.clear();
    }


    @Override
    public <T> EntityResult<T> create(T entity) {
        EntityManager em = getEntityManager();
        em.persist(entity);
        return makeEntityResult(entity);
    }

    @Override
    public <T> EntityResult<T> read(Class<T> entityType, Object key){
        EntityManager em = getEntityManager();
        T entity = em.find(entityType, key);
        return makeEntityResult(entity);
    }

    @Override
    public <T> EntityResult<T> update(T entity){
        EntityManager em = getEntityManager();
        T response = em.merge(entity);
        em.flush();
        return makeEntityResult(response);
    }

    @Override
    public <T> void delete(T entity){
        EntityManager em = getEntityManager();
        entity = em.merge(entity);
        em.remove(entity);
        em.flush();
    }

    @Override
    public <T> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query) {
        EntityManager em = getEntityManager();
        ClassTreeMetadata classTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(entityType);
        TypedQuery<T> listQuery = cto2QueryTranslator.constructListQuery(em, entityType, classTreeMetadata, query);
        TypedQuery<Long> countQuery = cto2QueryTranslator.constructCountQuery(em, entityType, classTreeMetadata, query);

        List<T> resultList = listQuery.getResultList();
        long count = countQuery.getSingleResult().longValue();

        CriteriaQueryResult<T> queryResult = new CriteriaQueryResult<T>(entityType);
        queryResult.setEntityCollection(resultList).setTotalCount(count).setStartIndex(query.getFirstResult());

        return queryResult;
    }

    private <T> EntityResult<T> makeEntityResult(T entity){
        if(entity == null)
            return null;
        EntityResult<T> entityResult = new EntityResult<T>();
        Class clz = entity.getClass();
        ClassMetadata classMetadata = dynamicEntityMetadataAccess.getClassMetadata(clz, false);
        Field idField = classMetadata.getIdField();
        try {
            entityResult.setIdKey(idField.getName())
                .setIdValue(idField.get(entity).toString())
                .setEntity(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return entityResult;
    }
}
