package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.config.ADataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
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
        basePackageClasses = TallyAdminDataService.class,
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
public class TallyAdminDataServiceBeanConfiguration extends ADataServiceBeanConfiguration {

    public TallyAdminDataServiceBeanConfiguration() {
        super();
    }

    @Override
    @Bean(name = TallyAdminDataServiceDefinition.TADMIN_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyAdminDataServiceDefinition.TADMIN_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Override
    @Bean(name = TallyAdminDataServiceDefinition.TADMIN_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}
