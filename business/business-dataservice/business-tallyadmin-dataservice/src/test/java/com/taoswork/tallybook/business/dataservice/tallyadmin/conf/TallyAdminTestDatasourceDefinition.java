package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinitionBase;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public class TallyAdminTestDatasourceDefinition
        extends MongoDatasourceDefinitionBase {

    @Override
    public String getDbName() {
        return "tallyadmin-test";
    }

    public void dropDatabase() {
        TallyAdminTestDatasourceDefinition def = this;
        final MongoClient mongo = new MongoClient(def.getDbHost(), def.getDbPort());
        final MongoDatabase db = mongo.getDatabase(def.getDbName());
        db.drop();
    }

}