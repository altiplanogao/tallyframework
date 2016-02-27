package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.TestDbSetting;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminCoreTestConfig extends AdminCoreConfig {
    @Override
    protected JpaDbSetting getDbSetting() {
        return new TestDbSetting();
    }
}
