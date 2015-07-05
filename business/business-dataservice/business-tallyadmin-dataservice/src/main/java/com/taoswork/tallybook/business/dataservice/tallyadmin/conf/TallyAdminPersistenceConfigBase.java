package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
public abstract class TallyAdminPersistenceConfigBase extends PersistenceConfigBase {

    public TallyAdminPersistenceConfigBase() {
        this(IDbSetting.DEFAULT_DB_SETTING);
    }

    TallyAdminPersistenceConfigBase(IDbSetting dbSetting) {
        super(new TallyAdminDataServiceDefinition(), dbSetting);
    }

    @Override
    @Bean
    public RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer(){
        return helperCreateRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    @Override
    @Bean(name = TallyAdminDataServiceDefinition.TADMIN_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return helperCreateServiceDataSource();
    }

    @Override
    @Bean(name = TallyAdminDataServiceDefinition.TADMIN_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return helperCreateEntityManagerFactory(
                this.serviceDataSource(),
                this.runtimeEnvironmentPropertyPlaceholderConfigurer());
    }

    @Override
    @Bean(name = TallyAdminDataServiceDefinition.TADMIN_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return helperCreateJpaTransactionManager(entityManagerFactory().getObject());
    }
}
