package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessDataServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.config.DataServiceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyBusinessDataService";

    public TallyBusinessDataService() {
        this(TallyBusinessDataServiceConfig.class,
                TallyBusinessPersistenceConfig.class, null);
    }

    public TallyBusinessDataService(
            Class<? extends TallyBusinessDataServiceConfig> dataServiceConf,
            Class<? extends TallyBusinessPersistenceConfig> persistenceConf,
            List<Class> annotatedClasses) {
        super(dataServiceConf, persistenceConf, annotatedClasses);
    }
}
