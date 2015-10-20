package com.taoswork.tallybook.business.datadomain.tallymanagement;

import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TallyManagementDbPersistenceConfig extends TestDbPersistenceConfigBase{
    public static final String TEST_DB_PU_NAME = "tallymanagementPU";

    @Override
    public String getPersistenceXml() {
        return "persistence-tallymanagement.xml";
    }

    @Override
    public String getDataSourceName() {
        return "test_tallymanagement";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}
