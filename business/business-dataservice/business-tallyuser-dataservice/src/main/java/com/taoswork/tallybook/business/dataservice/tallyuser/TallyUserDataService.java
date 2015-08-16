package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserDataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
public class TallyUserDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyUserDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyUserDataService() {
        this(TallyUserDataServiceBeanConfiguration.class, null);
    }

    TallyUserDataService(
            Class<? extends TallyUserDataServiceBeanConfiguration> dataServiceConf,
            List<Class> annotatedClasses) {
        super(new TallyUserDataServiceDefinition(), dataServiceConf, annotatedClasses);
    }
}
