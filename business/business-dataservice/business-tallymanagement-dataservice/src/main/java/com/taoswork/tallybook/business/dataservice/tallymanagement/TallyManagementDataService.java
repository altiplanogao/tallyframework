package com.taoswork.tallybook.business.dataservice.tallymanagement;

import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementDataServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyManagementDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyManagementDataService";

    TallyManagementDataService(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    public TallyManagementDataService() {
        super(new Class<?>[]{
                TallyManagementPersistenceConfig.class,
                TallyManagementDataServiceConfig.class});
    }
}
