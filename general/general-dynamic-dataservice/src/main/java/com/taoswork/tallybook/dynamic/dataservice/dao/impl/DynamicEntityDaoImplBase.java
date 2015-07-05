package com.taoswork.tallybook.dynamic.dataservice.dao.impl;

import com.taoswork.tallybook.dynamic.dataservice.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.query.translator.Cto2QueryTranslator;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.query.translator.impl.Cto2QueryTranslatorImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
    public <T> T persist(T entity) {
        EntityManager em = getEntityManager();
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Override
    public <T> T find(Class<T> entityClz, Object key){
        EntityManager em = getEntityManager();
        return em.find(entityClz, key);
    }

    @Override
    public <T> T update(T entity){
        EntityManager em = getEntityManager();
        T response = em.merge(entity);
        em.flush();
        return response;
    }

    @Override
    public void remove(Object entity){
        EntityManager em = getEntityManager();
        entity = em.merge(entity);
        em.remove(entity);
        em.flush();
    }

    @Override
    public <T> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) {
        EntityManager em = getEntityManager();
        ClassTreeMetadata classTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(entityClz);
        TypedQuery<T> listQuery = cto2QueryTranslator.constructListQuery(em, entityClz, classTreeMetadata, query);
        TypedQuery<Long> countQuery = cto2QueryTranslator.constructCountQuery(em, entityClz, classTreeMetadata, query);

        List<T> resultList = listQuery.getResultList();
        long count = countQuery.getSingleResult().longValue();

        CriteriaQueryResult<T> queryResult = new CriteriaQueryResult<T>();
        queryResult.setEntityCollection(resultList).setTotalCount(count).setStartIndex(query.getFirstResult());

        return queryResult;
    }
}
