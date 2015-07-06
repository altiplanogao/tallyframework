package com.taoswork.tallybook.business.dataservice.tallymanagement.conf;

import com.taoswork.tallybook.business.dataservice.tallymanagement.TallyManagementDataService;
import com.taoswork.tallybook.business.dataservice.tallymanagement.TallyManagementDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
import com.taoswork.tallybook.general.dataservice.support.config.ADataServiceBeanConfiguration;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.IDbSetting;
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
           basePackageClasses = TallyManagementDataService.class,
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
public class TallyManagementDataServiceBeanConfiguration extends ADataServiceBeanConfiguration {

    public TallyManagementDataServiceBeanConfiguration() {
        this(IDbSetting.DEFAULT_DB_SETTING);
    }

    public TallyManagementDataServiceBeanConfiguration(IDbSetting dbSetting) {
        super(dbSetting);
    }

    @Override
    @Bean(name = TallyManagementDataServiceDefinition.TMANAGEMENT_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyManagementDataServiceDefinition.TMANAGEMENT_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Override
    @Bean(name = TallyManagementDataServiceDefinition.TMANAGEMENT_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }

}
