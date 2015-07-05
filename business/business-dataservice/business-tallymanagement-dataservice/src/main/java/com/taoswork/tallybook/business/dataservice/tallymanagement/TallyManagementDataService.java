package com.taoswork.tallybook.business.dataservice.tallymanagement;

import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementDataServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.config.DataServiceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyManagementDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyManagementDataService";

    public TallyManagementDataService() {
        this(TallyManagementDataServiceConfig.class,
                TallyManagementPersistenceConfig.class, null);
    }

    public TallyManagementDataService(
            Class<? extends TallyManagementDataServiceConfig> dataServiceConf,
            Class<? extends TallyManagementPersistenceConfig> persistenceConf,
            List<Class> annotatedClasses) {
        super(dataServiceConf, persistenceConf, annotatedClasses);
    }

}
