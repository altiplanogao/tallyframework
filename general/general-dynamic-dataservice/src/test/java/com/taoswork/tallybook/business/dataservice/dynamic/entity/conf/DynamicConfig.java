package com.taoswork.tallybook.business.dataservice.dynamic.entity.conf;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.impl.EntityDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.dao.impl.DynamicEntityDaoImplBase;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.impl.DynamicEntityMetadataAccessImplBase;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.impl.DynamicEntityServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
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

    @Bean(name = EntityMetadataService.SERVICE_NAME)
    EntityMetadataService metadataService(){
        return new EntityMetadataServiceImpl();
    }

    @Bean(name = EntityDescriptionService.SERVICE_NAME)
    EntityDescriptionService entityDescriptionService(){
        return new EntityDescriptionServiceImpl();
    }
}
