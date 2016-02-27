package com.taoswork.tallybook.business.dataservice.tallymanagement;

import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementDataServiceBeanConfiguration;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.service.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyManagementDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyManagementJpaDatasourceDefinition.DATA_SERVICE_NAME;

    public TallyManagementDataService(JpaDbSetting dbSetting) {
        this(dbSetting, TallyManagementDataServiceBeanConfiguration.class, null);
    }

    public TallyManagementDataService(JpaDbSetting dbSetting,
                                      Class<? extends TallyManagementDataServiceBeanConfiguration> dataServiceConf,
                                      List<Class> annotatedClasses) {
        super(new TallyManagementJpaDatasourceDefinition(), dbSetting, dataServiceConf, annotatedClasses);
    }

}
