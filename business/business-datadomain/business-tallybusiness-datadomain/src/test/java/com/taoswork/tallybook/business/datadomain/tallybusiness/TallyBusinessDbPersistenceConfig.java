package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.testmaterial.jpa.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TallyBusinessDbPersistenceConfig extends TestDbPersistenceConfigBase {
    public static final String TEST_DB_PU_NAME = "tallybusinessPU";

    @Override
    public String getPersistenceXml() {
        return "persistence-tallybusiness.xml";
    }

    @Override
    public String getDataSourceName() {
        return "test_tallybusiness";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}
