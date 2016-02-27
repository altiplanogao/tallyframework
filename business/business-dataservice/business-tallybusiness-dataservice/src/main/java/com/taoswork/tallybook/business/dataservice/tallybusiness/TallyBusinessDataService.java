package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessDataServiceBeanConfiguration;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.service.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyBusinessJpaDatasourceDefinition.DATA_SERVICE_NAME;

    public TallyBusinessDataService(JpaDbSetting dbSetting) {
        this(dbSetting, TallyBusinessDataServiceBeanConfiguration.class, null);
    }

    public TallyBusinessDataService(JpaDbSetting dbSetting,
                                    Class<? extends TallyBusinessDataServiceBeanConfiguration> dataServiceConf,
                                    List<Class> annotatedClasses) {
        super(new TallyBusinessJpaDatasourceDefinition(), dbSetting, dataServiceConf, annotatedClasses);
    }
}
