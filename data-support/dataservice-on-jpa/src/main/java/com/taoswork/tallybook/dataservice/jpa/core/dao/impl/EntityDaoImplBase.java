package com.taoswork.tallybook.dataservice.jpa.core.dao.impl;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.jpa.core.dao.EntityDao;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.translator.Cto2QueryTranslator;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.translator.impl.Cto2QueryTranslatorImpl;
import com.taoswork.tallybook.dataservice.jpa.core.metaaccess.JpaEntityMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public abstract class EntityDaoImplBase implements EntityDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(EntityDaoImplBase.class);

    @Resource(name = JpaEntityMetaAccess.COMPONENT_NAME)
    protected JpaEntityMetaAccess entityMetaAccess;

    private final Cto2QueryTranslator cto2QueryTranslator;

    public EntityDaoImplBase() {
        cto2QueryTranslator = new Cto2QueryTranslatorImpl();
    }

    public abstract EntityManager getEntityManager();
//
//    @Override
//    public void flush() {
//        EntityManager em = getEntityManager();
//        em.flush();
//    }
//
//    @Override
//    public void detach(Object entity) {
//        EntityManager em = getEntityManager();
//        em.detach(entity);
//    }
//
//    @Override
//    public void refresh(Object entity) {
//        EntityManager em = getEntityManager();
//        em.refresh(entity);
//    }
//
//    @Override
//    public void clear() {
//        EntityManager em = getEntityManager();
//        em.clear();
//    }
//

    @Override
    public <T extends Persistable> T create(T entity) {
        EntityManager em = getEntityManager();
        em.persist(entity);
        return entity;
    }

    @Override
    public <T extends Persistable> T read(Class<T> entityType, Object key) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityType, key);
        return entity;
    }

    @Override
    public <T extends Persistable> T update(T entity) {
        EntityManager em = getEntityManager();
        T response = em.merge(entity);
        em.flush();
        return response;
    }

    @Override
    public <T extends Persistable> void delete(T entity) {
        EntityManager em = getEntityManager();
        entity = em.merge(entity);
        em.remove(entity);
        em.flush();
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query) {
        EntityManager em = getEntityManager();
        IClassMeta classTreeMeta = entityMetaAccess.getClassTreeMeta(entityType);
        TypedQuery<T> listQuery = cto2QueryTranslator.constructListQuery(em, entityType, classTreeMeta, query);
        TypedQuery<Long> countQuery = cto2QueryTranslator.constructCountQuery(em, entityType, classTreeMeta, query);

        List<T> resultList = listQuery.getResultList();
        long count = countQuery.getSingleResult().longValue();

        CriteriaQueryResult<T> queryResult = new CriteriaQueryResult<T>(entityType);
        queryResult.setEntityCollection(resultList).setTotalCount(count).setStartIndex(query.getFirstResult());

        return queryResult;
    }

}
