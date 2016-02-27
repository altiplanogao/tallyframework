package com.taoswork.tallybook.dataservice.mongo.core.entityservice;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.core.SecuredCrudqAccessor;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.dataservice.mongo.core.query.MongoQueryTranslator;
import com.taoswork.tallybook.dataservice.mongo.core.query.impl.MongoQueryTranslatorImpl;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public class SecuredEntityAccess extends SecuredCrudqAccessor {
    public static final String COMPONENT_NAME = "SecuredEntityAccess";

    @Resource(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    private AdvancedDatastore datastore;

    private MongoQueryTranslator queryTranslator = new MongoQueryTranslatorImpl();

    @Override
    protected <T extends Persistable> T doCreate(T entity) {
        Key<T> key = datastore.save(entity);
        return entity;
    }

    @Override
    protected <T extends Persistable> T doRead(Class<T> entityRootClz, Object key) {
        return datastore.get(entityRootClz, key);
    }

    @Override
    protected <T extends Persistable> T doUpdate(T entity) {
        Key<T> key = datastore.save(entity);
        return entity;
    }

    @Override
    protected <T extends Persistable> void doDelete(T entity) {
        datastore.delete(entity);
    }

    @Override
    protected <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> entityRootClz, CriteriaTransferObject cto) {
        IClassMeta classTreeMeta = entityMetaAccess.getClassTreeMeta(entityRootClz);
        Query<T> q = queryTranslator.constructListQuery(datastore, entityRootClz, classTreeMeta, cto);
        Query<T> qc = queryTranslator.constructCountQuery(datastore, entityRootClz, classTreeMeta, cto);

        List<T> resultList = q.asList();
        long count = qc.countAll();

        CriteriaQueryResult<T> queryResult = new CriteriaQueryResult<T>(entityRootClz);
        queryResult.setEntityCollection(resultList).setTotalCount(count).setStartIndex(cto.getFirstResult());

        return queryResult;
    }
}
