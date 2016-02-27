package com.taoswork.tallybook.dataservice.mongo.servicemockup;

import com.taoswork.tallybook.dataservice.annotations.DataService;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.core.MongoDataServiceBase;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource.TallyMockupPersistableConfiguration;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@DataService
public class TallyMockupMongoDataService
        extends MongoDataServiceBase<
        TallyMockupPersistableConfiguration,
        MongoDatasourceConfiguration> {

    public TallyMockupMongoDataService() {
        this(TallyMockupMongoDatasourceConfiguration.class);
    }

    public TallyMockupMongoDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyMockupMongoDataServiceDefinition(),
                TallyMockupPersistableConfiguration.class,
                dSrcConfClz);
    }
}
