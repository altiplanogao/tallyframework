package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.HsqlDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminCoreTestConfig extends AdminCoreConfig {
    @Override
    protected IDbSetting getDbSetting() {
        return new HsqlDbSetting();
    }
}
