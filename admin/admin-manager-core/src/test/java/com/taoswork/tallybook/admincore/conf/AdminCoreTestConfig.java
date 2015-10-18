package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.TestDbSetting;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminCoreTestConfig extends AdminCoreConfig {
    @Override
    protected IDbSetting getDbSetting() {
        return new TestDbSetting();
    }
}
