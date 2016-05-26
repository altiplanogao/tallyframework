package com.taoswork.tallybook.authority.solution.mockup.service.datasource;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinitionBase;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class AuthSolutionDatasourceConfiguration extends MongoDatasourceConfiguration {
    @Override
    protected MongoDatasourceDefinition createDatasourceDefinition() {
        return new DatasourceDefinition();
    }

    /**
     * Created by Gao Yuan on 2016/2/15.
     */
    public static final class DatasourceDefinition
            extends MongoDatasourceDefinitionBase {

        @Override
        public String getDbName() {
            return "auth-test";
        }

        public void dropDatabase() {
            DatasourceDefinition def = this;
            final MongoClient mongo = new MongoClient(def.getServerAddress());
            final MongoDatabase db = mongo.getDatabase(def.getDbName());
            db.drop();
        }
    }
}
