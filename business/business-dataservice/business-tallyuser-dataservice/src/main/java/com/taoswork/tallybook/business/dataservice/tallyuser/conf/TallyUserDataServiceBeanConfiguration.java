package com.taoswork.tallybook.business.dataservice.tallyuser.conf;

import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataServiceDefinition;
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
        basePackageClasses = TallyUserDataService.class,
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
public class TallyUserDataServiceBeanConfiguration extends ADataServiceBeanConfiguration {

    public TallyUserDataServiceBeanConfiguration() {
        super();
    }

    @Override
    @Bean(name = TallyUserDataServiceDefinition.TUSER_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyUserDataServiceDefinition.TUSER_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Override
    @Bean(name = TallyUserDataServiceDefinition.TUSER_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}
