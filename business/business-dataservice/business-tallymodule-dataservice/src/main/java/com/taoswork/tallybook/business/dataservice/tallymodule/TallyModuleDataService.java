package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModuleDatasourceConfiguration;
import com.taoswork.tallybook.business.dataservice.tallymodule.conf.TallyModulePersistableConfiguration;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.core.MongoDataServiceBase;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataService
        extends MongoDataServiceBase<
                TallyModulePersistableConfiguration,
                MongoDatasourceConfiguration> {
    public static final String COMPONENT_NAME = TallyModuleDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyModuleDataService() {
        this(TallyModuleDatasourceConfiguration.class);
    }

    public TallyModuleDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyModuleDataServiceDefinition(), TallyModulePersistableConfiguration.class, dSrcConfClz);
    }
}