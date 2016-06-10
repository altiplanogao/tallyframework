package com.taoswork.tallybook.dataservice.mongo.config;

import com.taoswork.tallybook.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.dataservice.mongo.core.entityprotect.valuecoper.MongoEntityCopierServiceImpl;
import com.taoswork.tallybook.dataservice.service.EntityCopierService;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taosworks.tallybook.descriptor.mongo.service.MongoMetaInfoServiceImpl;
import com.taosworks.tallybook.descriptor.mongo.service.MongoMetaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public abstract class MongoDatasourceConfiguration implements IDatasourceConfiguration {

    protected abstract MongoDatasourceDefinition createDatasourceDefinition();

    @Bean(name = DATA_SOURCE_PATH_DEFINITION)
    public MongoDatasourceDefinition mongoDatasourceDefinition() {
        return createDatasourceDefinition();
    }

    @Override
    @Bean(name = MetaService.SERVICE_NAME)
    public MetaService metadataService() {
        return new MongoMetaServiceImpl();
    }

    @Override
    @Bean(name = MetaInfoService.SERVICE_NAME)
    public MetaInfoService metaInfoService() {
        MetaInfoService metaInfoService = new MongoMetaInfoServiceImpl();
        return metaInfoService;
    }

    @Override
    @Bean(name = EntityCopierService.COMPONENT_NAME)
    public EntityCopierService entityValueCopierService() {
        return new MongoEntityCopierServiceImpl();
    }
}
