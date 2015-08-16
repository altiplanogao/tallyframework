package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessDataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyBusinessDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyBusinessDataService() {
        this(TallyBusinessDataServiceBeanConfiguration.class, null);
    }

    public TallyBusinessDataService(
            Class<? extends TallyBusinessDataServiceBeanConfiguration> dataServiceConf,
            List<Class> annotatedClasses) {
        super(new TallyBusinessDataServiceDefinition(), dataServiceConf, annotatedClasses);
    }
}
