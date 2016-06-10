package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserDatasourceConfiguration;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserPersistableConfiguration;
import com.taoswork.tallybook.dataservice.annotations.DataService;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.core.MongoDataServiceBase;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
public class TallyUserDataService
        extends MongoDataServiceBase<
        TallyUserPersistableConfiguration,
        MongoDatasourceConfiguration> {
    public static final String COMPONENT_NAME = TallyUserDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyUserDataService() {
        this(TallyUserDatasourceConfiguration.class);
    }

    public TallyUserDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyUserDataServiceDefinition(), TallyUserPersistableConfiguration.class, dSrcConfClz);
    }
}
