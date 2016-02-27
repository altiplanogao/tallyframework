package com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinitionBase;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public final class TallyMockupMongoDatasourceDefinition
        extends MongoDatasourceDefinitionBase {

    @Override
    public String getDbName() {
        return "DataServiceTestMockup";
    }

    public void dropDatabase() {
        TallyMockupMongoDatasourceDefinition def = this;
        final MongoClient mongo = new MongoClient(def.getDbHost(), def.getDbPort());
        final MongoDatabase db = mongo.getDatabase(def.getDbName());
        db.drop();
    }
}
