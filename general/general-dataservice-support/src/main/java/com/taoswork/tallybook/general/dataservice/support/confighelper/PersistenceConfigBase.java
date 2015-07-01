package com.taoswork.tallybook.general.dataservice.support.confighelper;

import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.service.impl.DynamicEntityServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.service.impl.DynamicServerEntityServiceImpl;
import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.confighelper.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.solution.property.PropertiesSubCollectionProvider;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public abstract class PersistenceConfigBase implements IPersistenceBeanList{

    private final PersistenceConfigHelper helper;
    private final IDataServiceDefinition dataServiceDefinition;

    protected PersistenceConfigBase(IDataServiceDefinition dsDef, IDbSetting dbSetting) {
        helper = new PersistenceConfigHelper(dsDef, dbSetting);
        this.dataServiceDefinition = dsDef;
    }

    @Bean(name = IDataServiceDefinition.DATA_SERVICE_DEFINITION_BEAN_NAME)
    public IDataServiceDefinition dataServiceDefinitionBean(){
        return this.dataServiceDefinition;
    }

    protected RuntimeEnvironmentPropertyPlaceholderConfigurer helperCreateRuntimeEnvironmentPropertyPlaceholderConfigurer(){
        return helper.createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    protected DataSource helperCreateServiceDataSource() {
        return helper.createDefaultDataSource();
    }

    protected AbstractEntityManagerFactoryBean helperCreateEntityManagerFactory(
            DataSource refDs,
            PropertiesSubCollectionProvider refPropertiesProvider) {
        return helper.createDefaultEntityManagerFactory(
                refDs,
                refPropertiesProvider);
    }

    protected JpaTransactionManager helperCreateJpaTransactionManager(EntityManagerFactory refEmf) {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setEntityManagerFactory(refEmf);
        return jtm;
    }

    @Bean(name = DynamicEntityService.COMPONENT_NAME)
    public DynamicEntityService dynamicEntityService(){
        return new DynamicEntityServiceImpl();
    }

    @Bean(name = DynamicServerEntityService.SERVICE_NAME)
    public DynamicServerEntityService dynamicServerEntityService(){
        return new DynamicServerEntityServiceImpl();
    }
}
