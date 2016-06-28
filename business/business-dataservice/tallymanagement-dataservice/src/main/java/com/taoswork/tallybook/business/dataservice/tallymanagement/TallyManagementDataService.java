package com.taoswork.tallybook.business.dataservice.tallymanagement;

import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementDatasourceConfiguration;
import com.taoswork.tallybook.business.dataservice.tallymanagement.conf.TallyManagementPersistableConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.core.MongoDataServiceBase;


/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyManagementDataService
        extends MongoDataServiceBase<
                TallyManagementPersistableConfiguration,
                MongoDatasourceConfiguration> {
    public static final String COMPONENT_NAME = TallyManagementDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyManagementDataService() {
        this(TallyManagementDatasourceConfiguration.class);
    }

    public TallyManagementDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyManagementDataServiceDefinition(), TallyManagementPersistableConfiguration.class, dSrcConfClz);
    }
}
