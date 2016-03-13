package com.taoswork.tallybook.module.elevator.dataservice;

import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.core.MongoDataServiceBase;
import com.taoswork.tallybook.module.elevator.dataservice.conf.ElevatorDatasourceConfiguration;
import com.taoswork.tallybook.module.elevator.dataservice.conf.ElevatorPersistableConfiguration;
import com.taoswork.tallybook.module.elevator.dataservice.conf.ModuleConfiguration;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class ElevatorModuleDataService
        extends MongoDataServiceBase<
        ElevatorPersistableConfiguration,
        MongoDatasourceConfiguration> {
    public static final String COMPONENT_NAME = ElevatorModuleDataServiceDefinition.DATA_SERVICE_NAME;

    public ElevatorModuleDataService() {
        this(ElevatorDatasourceConfiguration.class);
    }

    public ElevatorModuleDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new ElevatorModuleDataServiceDefinition(), ElevatorPersistableConfiguration.class, dSrcConfClz,
                ModuleConfiguration.class);
    }
}