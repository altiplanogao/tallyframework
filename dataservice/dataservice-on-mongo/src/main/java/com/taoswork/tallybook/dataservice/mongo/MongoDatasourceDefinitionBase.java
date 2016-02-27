package com.taoswork.tallybook.dataservice.mongo;

import com.mongodb.ServerAddress;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class MongoDatasourceDefinitionBase implements MongoDatasourceDefinition{
    @Override
    public String getDbHost() {
        return ServerAddress.defaultHost();
    }

    @Override
    public int getDbPort() {
        return ServerAddress.defaultPort();
    }

    @Override
    public Class getRootDataClass() {
        return null;
    }
}
