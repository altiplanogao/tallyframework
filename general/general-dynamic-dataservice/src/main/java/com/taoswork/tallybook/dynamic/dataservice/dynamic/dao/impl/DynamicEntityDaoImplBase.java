package com.taoswork.tallybook.dynamic.dataservice.dynamic.dao.impl;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.query.translator.QueryTranslator;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.query.translator.impl.QueryTranslatorImpl;

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

    private final QueryTranslator queryTranslator;
    
    public DynamicEntityDaoImplBase(){
        queryTranslator = new QueryTranslatorImpl();
    }

    public abstract EntityManager getEntityManager();
    
    @Override
    public void flush(){
        getEntityManager().flush();
    }

    @Override
    public void detach(Object entity){
        getEntityManager().detach(entity);
    }

    @Override
    public void refresh(Object entity){
        getEntityManager().refresh(entity);
    }

    @Override
    public void clear(){
        getEntityManager().clear();
    }


    @Override
    public <T> T persist(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    @Override
    public <T> T find(Class<T> entityClz, Object key){
        return getEntityManager().find(entityClz, key);
    }

    @Override
    public <T> T merge(T entity){
        T response = getEntityManager().merge(entity);
        getEntityManager().flush();
        return response;
    }

    @Override
    public void remove(Object entity){
        getEntityManager().remove(entity);
        getEntityManager().flush();
    }

    public <T> List<T> query(Class<T> entityClz, CriteriaTransferObject query) {
        ClassTreeMetadata classTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(entityClz);
        TypedQuery<T> typedQuery = queryTranslator.constructListQuery(getEntityManager(), entityClz, classTreeMetadata, query);
        return typedQuery.getResultList();
    }
}
