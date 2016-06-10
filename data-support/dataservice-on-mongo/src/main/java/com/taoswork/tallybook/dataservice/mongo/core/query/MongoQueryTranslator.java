package com.taoswork.tallybook.dataservice.mongo.core.query;

import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;

/**
 * Created by Gao Yuan on 2016/2/17.
 */
public interface MongoQueryTranslator {
    <T> Query<T> constructListQuery(
            AdvancedDatastore datastore,
            String collection,
            Class<T> entityClz,
            IClassMeta classTreeMeta,
            CriteriaTransferObject cto);

    <T> Query<T> constructCountQuery(
            AdvancedDatastore datastore,
            String collection,
            Class<T> entityClz,
            IClassMeta classTreeMeta,
            CriteriaTransferObject cto);
}
