package com.taoswork.tallybook.business.dataservice.tallymodule.conf;

import com.taoswork.tallybook.business.dataservice.tallymodule.TallyModuleDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.confighelper.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.confighelper.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
abstract class TallyModulePersistenceConfigBase extends PersistenceConfigBase {

    public TallyModulePersistenceConfigBase() {
        this(IDbSetting.DEFAULT_DB_SETTING);
    }

    TallyModulePersistenceConfigBase(IDbSetting dbSetting) {
        super(new TallyModuleDataServiceDefinition(), dbSetting);
    }

    @Override
    @Bean
    public RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer(){
        return helperCreateRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    @Override
    @Bean(name = TallyModuleDataServiceDefinition.TMODULE_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return helperCreateServiceDataSource();
    }

    @Override
    @Bean(name = TallyModuleDataServiceDefinition.TMODULE_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return helperCreateEntityManagerFactory(
                this.serviceDataSource(),
                this.runtimeEnvironmentPropertyPlaceholderConfigurer());
    }

    @Override
    @Bean(name = TallyModuleDataServiceDefinition.TMODULE_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return helperCreateJpaTransactionManager(entityManagerFactory().getObject());
    }
}
