package com.taoswork.tallybook.dataservice.jpa.config.db;

import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import org.springframework.context.annotation.Bean;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public abstract class IDbConfig {
    public static final String DB_SETTING = "dbSetting";

    @Bean(name = DB_SETTING)
    public abstract JpaDbSetting theDbSetting();
}
