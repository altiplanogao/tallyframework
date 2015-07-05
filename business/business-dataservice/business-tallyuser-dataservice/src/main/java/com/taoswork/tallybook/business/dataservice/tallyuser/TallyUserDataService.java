package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserDataServiceConfig;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserPersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;
import com.taoswork.tallybook.general.dataservice.support.config.DataServiceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
//@Component(TallyUserDataService.COMPONENT_NAME)
public class TallyUserDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyUserDataService";

    public TallyUserDataService() {
        this(TallyUserDataServiceConfig.class,
                TallyUserPersistenceConfig.class, null);
    }

    TallyUserDataService(
            Class<? extends TallyUserDataServiceConfig> dataServiceConf,
            Class<? extends TallyUserPersistenceConfigBase> persistenceConf,
            List<Class> annotatedClasses) {
        super(dataServiceConf, persistenceConf, annotatedClasses);
    }
}
