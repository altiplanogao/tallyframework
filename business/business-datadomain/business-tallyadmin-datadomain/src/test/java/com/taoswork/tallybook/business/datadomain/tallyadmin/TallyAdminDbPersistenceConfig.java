package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TallyAdminDbPersistenceConfig extends TestDbPersistenceConfigBase{
    public static final String TEST_DB_PU_NAME = "tallyadminPU";

    @Override
    public String getPersistenceXml() {
        return "persistence-tallyadmin.xml";
    }

    @Override
    public String getDataSourceName() {
        return "test_tallyadmin";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}
