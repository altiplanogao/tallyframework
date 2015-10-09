package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserDataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
public class TallyUserDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyUserDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyUserDataService(IDbSetting dbSetting) {
        this(dbSetting, TallyUserDataServiceBeanConfiguration.class, null);
    }

    TallyUserDataService(IDbSetting dbSetting,
                         Class<? extends TallyUserDataServiceBeanConfiguration> dataServiceConf,
                         List<Class> annotatedClasses) {
        super(new TallyUserDataServiceDefinition(), dbSetting, dataServiceConf, annotatedClasses);
    }
}
