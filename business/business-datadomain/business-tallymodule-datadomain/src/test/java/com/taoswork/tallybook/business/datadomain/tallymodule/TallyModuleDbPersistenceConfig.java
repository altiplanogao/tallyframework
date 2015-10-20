package com.taoswork.tallybook.business.datadomain.tallymodule;

import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TallyModuleDbPersistenceConfig extends TestDbPersistenceConfigBase{
    public static final String TEST_DB_PU_NAME = "tallymodulePU";

    @Override
    public String getPersistenceXml() {
        return "persistence-tallymodule.xml";
    }

    @Override
    public String getDataSourceName() {
        return "tallymoduleDb";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}