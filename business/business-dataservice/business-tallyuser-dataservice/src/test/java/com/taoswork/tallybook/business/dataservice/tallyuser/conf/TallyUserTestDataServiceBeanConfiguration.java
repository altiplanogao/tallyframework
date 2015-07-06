package com.taoswork.tallybook.business.dataservice.tallyuser.conf;

import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.HsqlDbSetting;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@Configuration
@EnableTransactionManagement
public class TallyUserTestDataServiceBeanConfiguration extends TallyUserDataServiceBeanConfiguration {

    public TallyUserTestDataServiceBeanConfiguration() {
        super(new HsqlDbSetting());
    }
}
