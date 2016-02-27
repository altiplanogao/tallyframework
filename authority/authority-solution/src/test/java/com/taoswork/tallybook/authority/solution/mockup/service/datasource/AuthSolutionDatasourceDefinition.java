package com.taoswork.tallybook.authority.solution.mockup.service.datasource;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinitionBase;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public final class AuthSolutionDatasourceDefinition
        extends MongoDatasourceDefinitionBase {

    @Override
    public String getDbName() {
        return "AuthSolutionTestMockup";
    }

    public void dropDatabase() {
        AuthSolutionDatasourceDefinition def = this;
        final MongoClient mongo = new MongoClient(def.getDbHost(), def.getDbPort());
        final MongoDatabase db = mongo.getDatabase(def.getDbName());
        db.drop();
    }
}
