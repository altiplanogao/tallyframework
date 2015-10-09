package com.taoswork.tallybook.business.dataservice.tallymanagement;

import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementDataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyManagementDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyManagementDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyManagementDataService(IDbSetting dbSetting) {
        this(dbSetting, TallyManagementDataServiceBeanConfiguration.class, null);
    }

    public TallyManagementDataService(IDbSetting dbSetting,
            Class<? extends TallyManagementDataServiceBeanConfiguration> dataServiceConf,
            List<Class> annotatedClasses) {
        super(new TallyManagementDataServiceDefinition(), dbSetting, dataServiceConf, annotatedClasses);
    }

}
