package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessDatasourceConfiguration;
import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TallyBusinessPersistableConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.core.MongoDataServiceBase;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataService
        extends MongoDataServiceBase<
                TallyBusinessPersistableConfiguration,
                MongoDatasourceConfiguration> {
    public static final String COMPONENT_NAME = TallyBusinessDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyBusinessDataService() {
        this(TallyBusinessDatasourceConfiguration.class);
    }

    public TallyBusinessDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyBusinessDataServiceDefinition(), TallyBusinessPersistableConfiguration.class, dSrcConfClz);
    }
}
