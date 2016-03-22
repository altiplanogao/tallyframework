package com.taoswork.tallybook.business.dataservice.tallybusiness.conf;

import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinitionBase;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class TallyBusinessDatasourceConfiguration extends MongoDatasourceConfiguration {
    @Override
    protected MongoDatasourceDefinition createDatasourceDefinition() {
        return new DatasourceDefinition();
    }

    /**
     * Created by Gao Yuan on 2016/2/16.
     */
    public static class DatasourceDefinition
            extends MongoDatasourceDefinitionBase {

        @Override
        public String getDbName() {
            return "tallybiz";
        }

    }
}