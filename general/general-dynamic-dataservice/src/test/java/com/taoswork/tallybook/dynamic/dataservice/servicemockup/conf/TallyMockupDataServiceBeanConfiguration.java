package com.taoswork.tallybook.dynamic.dataservice.servicemockup.conf;

import com.taoswork.tallybook.dynamic.dataservice.config.ADataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
@Configuration
@ComponentScan(
        basePackageClasses = TallyMockupDataService.class,
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = {
                        Dao.class,
                        EntityService.class})},
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = {Configuration.class}
        )}
)
@EnableTransactionManagement
public class TallyMockupDataServiceBeanConfiguration extends ADataServiceBeanConfiguration {

    @Override
    @Bean(name = TallyMockupDataServiceDefinition.TMOCKUP_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyMockupDataServiceDefinition.TMOCKUP_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        AbstractEntityManagerFactoryBean entityManagerFactory = super.entityManagerFactory();
        Class dialect = helper.getDbSetting().hibernateDialect();
        ((LocalContainerEntityManagerFactoryBean)entityManagerFactory).setPersistenceUnitPostProcessors(
            TestDbPersistenceConfig.createPersistenceUnitPostProcessor(dialect)
        );
        return entityManagerFactory;
    }

    @Override
    @Bean(name = TallyMockupDataServiceDefinition.TMOCKUP_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}
