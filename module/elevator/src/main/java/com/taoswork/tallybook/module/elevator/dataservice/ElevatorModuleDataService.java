package com.taoswork.tallybook.module.elevator.dataservice;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.core.MongoDataServiceBase;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.module.base.datadomain.CenterDefinition;
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

    @Override
    protected void postConstruct() {
        super.postConstruct();
        IEntityService<PersistableDocument> entityService = this.getService(IEntityService.COMPONENT_NAME);
        try {
            CenterDefinition centerDefinition = entityService.queryOne(CenterDefinition.class, null);
            if(centerDefinition == null){
                centerDefinition = new CenterDefinition();
                centerDefinition.setServiceUrl("http://localhost:8080/module.center");
                centerDefinition.setPublicKey("");
                entityService.create(CenterDefinition.class, centerDefinition);
            }
        }catch (ServiceException e){
            throw new RuntimeException(e);
        }
    }
}