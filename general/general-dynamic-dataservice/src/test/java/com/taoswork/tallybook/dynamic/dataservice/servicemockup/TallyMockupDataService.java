package com.taoswork.tallybook.dynamic.dataservice.servicemockup;

import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.conf.TallyMockupDataServiceBeanConfiguration;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
public class TallyMockupDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyMockupDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyMockupDataService() {
        this(TallyMockupDataServiceBeanConfiguration.class, null);
    }

    TallyMockupDataService(
        Class<? extends TallyMockupDataServiceBeanConfiguration> dataServiceConf,
        List<Class> annotatedClasses) {
        super(new TallyMockupDataServiceDefinition(), dataServiceConf, annotatedClasses);
    }
}
