package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModulePersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModuleServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyModuleDataService";

    TallyModuleDataService(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    public TallyModuleDataService() {
        super(new Class<?>[]{
                TallyModulePersistenceConfig.class,
                TallyModuleServiceConfig.class});
    }
}
