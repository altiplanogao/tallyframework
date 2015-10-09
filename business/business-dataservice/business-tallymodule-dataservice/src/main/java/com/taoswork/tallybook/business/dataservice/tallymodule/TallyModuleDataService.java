package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModuleDataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyModuleDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyModuleDataService(IDbSetting dbSetting) {
        this(dbSetting, TallyModuleDataServiceBeanConfiguration.class, null);
    }

    public TallyModuleDataService(IDbSetting dbSetting,
                                  Class<? extends TallyModuleDataServiceBeanConfiguration> dataServiceConf,
                                  List<Class> annotatedClasses) {
        super(new TallyModuleDataServiceDefinition(), dbSetting, dataServiceConf, annotatedClasses);
    }
}
