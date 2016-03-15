package com.taoswork.tallybook.dataservice.jpa.config;

import com.taoswork.tallybook.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.jpa.core.entityprotect.valuecoper.JpaEntityCopierServiceImpl;
import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.service.EntityCopierService;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaInfoServiceImpl;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.descriptor.service.MetaService;
import org.springframework.context.annotation.Bean;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class JpaDatasourceConfiguration implements IDatasourceConfiguration {

    protected abstract JpaDatasourceDefinition createDatasourceDefinition();

    @Bean(name = DATA_SOURCE_PATH_DEFINITION)
    public JpaDatasourceDefinition mongoDatasourceDefinition() {
        return createDatasourceDefinition();
    }

    @Override
    @Bean(name = MetaService.SERVICE_NAME)
    public MetaService metadataService() {
        return new JpaMetaServiceImpl();
    }

    @Override
    @Bean(name = MetaInfoService.SERVICE_NAME)
    public MetaInfoService metaInfoService() {
        MetaInfoService metaInfoService = new JpaMetaInfoServiceImpl();
        return metaInfoService;
    }

    @Override
    @Bean(name = EntityCopierService.COMPONENT_NAME)
    public EntityCopierService entityValueCopierService() {
        return new JpaEntityCopierServiceImpl();
    }
}
