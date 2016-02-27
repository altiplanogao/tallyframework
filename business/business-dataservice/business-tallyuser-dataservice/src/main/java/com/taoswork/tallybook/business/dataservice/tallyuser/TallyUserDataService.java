package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserJpaDatasourceBeanConfiguration;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserJpaDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.annotations.DataService;
import com.taoswork.tallybook.dataservice.jpa.config.JpaDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallybook.dataservice.jpa.core.JpaDataServiceBase;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
public class TallyUserDataService
        extends JpaDataServiceBase<TallyUserJpaDatasourceBeanConfiguration,
        JpaDatasourceConfiguration,
        IDbConfig> {
    public static final String COMPONENT_NAME = TallyUserDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyUserDataService(Class<? extends JpaDatasourceConfiguration> dSrcConfClz,
                                Class<? extends IDbConfig> dbConf) {
        super(new TallyUserDataServiceDefinition(), TallyUserJpaDatasourceBeanConfiguration.class,
                dSrcConfClz, dbConf);
    }

    public TallyUserDataService(Class<? extends IDbConfig> dbConf) {
        this(TallyUserJpaDatasourceConfiguration.class, dbConf);
    }
}
