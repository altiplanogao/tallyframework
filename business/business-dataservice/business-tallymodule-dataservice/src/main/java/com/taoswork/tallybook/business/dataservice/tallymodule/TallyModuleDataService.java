package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModulePersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModuleDataServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.config.DataServiceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyModuleDataService";

    public TallyModuleDataService() {
        this(TallyModuleDataServiceConfig.class,
                TallyModulePersistenceConfig.class, null);
    }

    public TallyModuleDataService(
            Class<? extends TallyModuleDataServiceConfig> dataServiceConf,
            Class<? extends TallyModulePersistenceConfig> persistenceConf,
            List<Class> annotatedClasses) {
        super(dataServiceConf, persistenceConf, annotatedClasses);
    }
}
