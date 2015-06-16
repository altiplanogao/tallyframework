package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessDataServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyBusinessDataService";

    TallyBusinessDataService(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    public TallyBusinessDataService() {
        super(new Class<?>[]{
                TallyBusinessPersistenceConfig.class,
                TallyBusinessDataServiceConfig.class});
    }
}
