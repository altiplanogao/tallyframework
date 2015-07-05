package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.HsqlDbSetting;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
@Configuration
@EnableTransactionManagement
public class TallyAdminTestPersistenceConfig extends TallyAdminPersistenceConfigBase {

    public TallyAdminTestPersistenceConfig() {
        super(new HsqlDbSetting());
    }
}
