package com.taoswork.tallybook.business.dataservice.tallyuser.conf;

import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/10.
 */

public class TallyUserPersistenceConfigBase extends PersistenceConfigBase {

    public TallyUserPersistenceConfigBase() {
        this(IDbSetting.DEFAULT_DB_SETTING);
    }

    TallyUserPersistenceConfigBase(IDbSetting dbSetting) {
        super(new TallyUserDataServiceDefinition(), dbSetting);
    }

    @Override
    @Bean
    public RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer(){
        return helperCreateRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    @Override
    @Bean(name = TallyUserDataServiceDefinition.TUSER_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return helperCreateServiceDataSource();
    }

    @Override
    @Bean(name = TallyUserDataServiceDefinition.TUSER_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return helperCreateEntityManagerFactory(
                this.serviceDataSource(),
                this.runtimeEnvironmentPropertyPlaceholderConfigurer());
    }

    @Override
    @Bean(name = TallyUserDataServiceDefinition.TUSER_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return helperCreateJpaTransactionManager(entityManagerFactory().getObject());
    }
}
