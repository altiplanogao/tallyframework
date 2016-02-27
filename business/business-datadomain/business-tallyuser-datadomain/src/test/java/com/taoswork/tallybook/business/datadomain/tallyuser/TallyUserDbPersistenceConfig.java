package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.testmaterial.jpa.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TallyUserDbPersistenceConfig extends TestDbPersistenceConfigBase {
    public static final String TEST_DB_PU_NAME = "tallyuserPU";

    @Override
    public String getPersistenceXml() {
        return "persistence-tallyuser.xml";
    }

    @Override
    public String getDataSourceName() {
        return "test_tallyuser";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}
