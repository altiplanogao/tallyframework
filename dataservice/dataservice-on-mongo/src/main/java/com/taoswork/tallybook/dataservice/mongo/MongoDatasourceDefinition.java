package com.taoswork.tallybook.dataservice.mongo;

import com.mongodb.ServerAddress;
import com.taoswork.tallybook.dataservice.DatasourceDefinition;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface MongoDatasourceDefinition extends DatasourceDefinition {
    public static final String MONGO_DATA_DEF_BEAN_NAME = "MongoDatasourceDefinition";
    public static final String DATASTORE_BEAN_NAME = "theDatastore";

    ServerAddress getServerAddress();

    String getDbName();

    Class getRootDataClass();
}
