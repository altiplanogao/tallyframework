package com.taoswork.tallybook.dataservice.mongo.dao;

import com.taoswork.tallybook.dataservice.core.entity.DaoBase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class DocumentDaoBase extends DaoBase {
    @Resource(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    protected Datastore datastore;

    @Resource(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    protected AdvancedDatastore advancedDatastore;
}
