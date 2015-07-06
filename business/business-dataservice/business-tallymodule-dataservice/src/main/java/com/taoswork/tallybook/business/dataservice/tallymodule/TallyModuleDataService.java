package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModuleDataServiceBeanConfiguration;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyModuleDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyModuleDataService() {
        this(TallyModuleDataServiceBeanConfiguration.class, null);
    }

    public TallyModuleDataService(
            Class<? extends TallyModuleDataServiceBeanConfiguration> dataServiceConf,
            List<Class> annotatedClasses) {
        super(new TallyModuleDataServiceDefinition(), dataServiceConf, annotatedClasses);
    }
}
