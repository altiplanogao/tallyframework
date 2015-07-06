package com.taoswork.tallybook.business.dataservice.tallymodule.conf;

import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleDataService;
import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleDataServiceDefinition;
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
        basePackageClasses = TallyModuleDataService.class,
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
public class TallyModuleDataServiceBeanConfiguration  extends ADataServiceBeanConfiguration {

    public TallyModuleDataServiceBeanConfiguration() {
        this(IDbSetting.DEFAULT_DB_SETTING);
    }

    public TallyModuleDataServiceBeanConfiguration(IDbSetting dbSetting) {
        super(dbSetting);
    }

    @Override
    @Bean(name = TallyModuleDataServiceDefinition.TMODULE_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyModuleDataServiceDefinition.TMODULE_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Override
    @Bean(name = TallyModuleDataServiceDefinition.TMODULE_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}

