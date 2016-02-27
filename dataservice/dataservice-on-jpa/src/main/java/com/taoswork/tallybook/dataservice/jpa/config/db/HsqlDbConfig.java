package com.taoswork.tallybook.dataservice.jpa.config.db;

import com.taoswork.tallybook.dataservice.jpa.config.db.setting.HsqlDbSetting;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
@Configuration
public class HsqlDbConfig extends IDbConfig {
    @Override
    public JpaDbSetting theDbSetting() {
        return new HsqlDbSetting();
    }
}
