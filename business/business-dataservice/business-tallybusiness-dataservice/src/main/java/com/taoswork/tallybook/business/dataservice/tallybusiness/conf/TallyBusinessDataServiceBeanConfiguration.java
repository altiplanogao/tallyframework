package com.taoswork.tallybook.business.dataservice.tallybusiness.conf;

import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataService;
import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessJpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.descriptor.jpa.config.ADataServiceBeanConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
@Configuration
@ComponentScan(
        basePackageClasses = TallyBusinessDataService.class,
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
public class TallyBusinessDataServiceBeanConfiguration extends ADataServiceBeanConfiguration {

    public TallyBusinessDataServiceBeanConfiguration() {
        super();
    }

    @Override
    @Bean(name = TallyBusinessJpaDatasourceDefinition.TBUSINESS_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyBusinessJpaDatasourceDefinition.TBUSINESS_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Override
    @Bean(name = TallyBusinessJpaDatasourceDefinition.TBUSINESS_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}
