package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModuleDataServiceBeanConfiguration;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.service.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyModuleJpaDatasourceDefinition.DATA_SERVICE_NAME;

    public TallyModuleDataService(JpaDbSetting dbSetting) {
        this(dbSetting, TallyModuleDataServiceBeanConfiguration.class, null);
    }

    public TallyModuleDataService(JpaDbSetting dbSetting,
                                  Class<? extends TallyModuleDataServiceBeanConfiguration> dataServiceConf,
                                  List<Class> annotatedClasses) {
        super(new TallyModuleJpaDatasourceDefinition(), dbSetting, dataServiceConf, annotatedClasses);
    }
}
