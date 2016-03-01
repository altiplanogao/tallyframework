package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallybook.dataservice.jpa.config.db.TestDbConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminCoreTestConfig extends AdminCoreConfig {
    @Override
    protected Class<? extends IDbConfig> getDbSetting() {
        return TestDbConfig.class;
    }
}
