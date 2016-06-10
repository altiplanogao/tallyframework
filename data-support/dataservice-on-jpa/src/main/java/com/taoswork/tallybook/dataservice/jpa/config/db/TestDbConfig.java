package com.taoswork.tallybook.dataservice.jpa.config.db;

import com.taoswork.tallybook.dataservice.jpa.config.db.setting.DerbyDbSetting;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
@Configuration
public class TestDbConfig extends IDbConfig {
    @Override
    public JpaDbSetting theDbSetting() {
        return new DerbyDbSetting();
    }
}
