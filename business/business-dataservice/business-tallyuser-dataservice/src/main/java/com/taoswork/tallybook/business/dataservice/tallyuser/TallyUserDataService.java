package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserDataServiceConfig;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
//@Component(TallyUserDataService.COMPONENT_NAME)
public class TallyUserDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyUserDataService";

    TallyUserDataService(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    public TallyUserDataService() {
        super(new Class<?>[]{
                TallyUserPersistenceConfig.class,
                TallyUserDataServiceConfig.class});
    }
}
