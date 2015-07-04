package com.taoswork.tallybook.dynamic.dataservice.datamork.conf;

import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.dataservice.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.dao.impl.DynamicEntityDaoImplBase;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.impl.DynamicEntityMetadataAccessImplBase;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.service.impl.DynamicEntityServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
@Configuration
public class DynamicConfig {
    @Bean(name = DynamicEntityService.COMPONENT_NAME)
    DynamicEntityService dynamicEntityService(){
        return new DynamicEntityServiceImpl();
    }

    @Bean(name = DynamicEntityDao.COMPONENT_NAME)
    DynamicEntityDao dynamicEntityDao(){
        return new DynamicEntityDaoImplBase() {
            @PersistenceContext(unitName = TestDbPersistenceConfig.TEST_DB_PU_NAME)
            private EntityManager entityManager2;

            @Override
            public EntityManager getEntityManager() {
                return entityManager2;
            }
        };
    }

    @Bean(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
    DynamicEntityMetadataAccess entityMetadataAccess(){
        return new DynamicEntityMetadataAccessImplBase() {

            @PersistenceContext(unitName = TestDbPersistenceConfig.TEST_DB_PU_NAME)
            private EntityManager entityManager2;

            @Override
            public EntityManager getEntityManager() {
                return entityManager2;
            }
        };
    }

    @Bean(name = MetadataService.SERVICE_NAME)
    MetadataService metadataService(){
        return new MetadataServiceImpl();
    }

    @Bean(name = MetaInfoService.SERVICE_NAME)
    MetaInfoService metaDescriptionService(){
        return new MetaInfoServiceImpl();
    }
}
