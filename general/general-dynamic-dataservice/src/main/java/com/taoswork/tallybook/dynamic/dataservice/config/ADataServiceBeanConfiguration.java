package com.taoswork.tallybook.dynamic.dataservice.config;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDelegate;
import com.taoswork.tallybook.dynamic.dataservice.config.beanlist.IDataServiceSupporterBeanList;
import com.taoswork.tallybook.dynamic.dataservice.config.beanlist.IEntityBeanList;
import com.taoswork.tallybook.dynamic.dataservice.config.beanlist.IGeneralBeanList;
import com.taoswork.tallybook.dynamic.dataservice.config.beanlist.IPersistenceBeanList;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.config.helper.DataServiceBeanCreationHelper;
import com.taoswork.tallybook.dynamic.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.dynamic.dataservice.core.description.impl.FriendlyMetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.impl.DynamicEntityServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerFactory;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.impl.PersistenceManagerImpl;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
@Import(StandAloneBeanConfiguration.class)
public abstract class ADataServiceBeanConfiguration
        implements
        ApplicationContextAware,
        IGeneralBeanList,
        IDataServiceSupporterBeanList,
        IEntityBeanList,
        IPersistenceBeanList {

    private static final Logger LOGGER = LoggerFactory.getLogger(ADataServiceBeanConfiguration.class);

    private final IDbSetting dbSetting;
    private IDataServiceDefinition dataServiceDefinition;
    protected DataServiceBeanCreationHelper helper;

    // **************************************************** //
    //  Constructor & initialize                            //
    // **************************************************** //
    protected ADataServiceBeanConfiguration(IDbSetting dbSetting){
        this.dbSetting = dbSetting;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext instanceof IDataServiceDelegate){
            IDataServiceDefinition dataServiceDefinition = ((IDataServiceDelegate)applicationContext).getDataServiceDefinition();
            this.dataServiceDefinition = dataServiceDefinition;
            helper = new DataServiceBeanCreationHelper(dataServiceDefinition, dbSetting);
        }else {
            LOGGER.error("'{}' expected to be loaded by IDataServiceDefinitionHolder", this.getClass().getName());
            throw new IllegalArgumentException(this.getClass().getName() + " expected to be loaded by IDataServiceDefinitionHolder");
        }
    }

    // **************************************************** //
    //  IGeneralBeanList                                    //
    // **************************************************** //

    @Override
    @Bean
    public BeanCreationMonitor beanCreationMonitor() {
        return new BeanCreationMonitor(dataServiceDefinition.getDataServiceName());
    }

    @Override
    @Bean(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    public String dataServiceName() {
        return dataServiceDefinition.getDataServiceName();
    }

    // **************************************************** //
    //  IDataServiceSupporterBeanList                       //
    // **************************************************** //

    @Override
    @Bean(name = IDataServiceDefinition.DATA_SERVICE_DEFINITION_BEAN_NAME)
    public IDataServiceDefinition dataServiceDefinitionBean(){
        return this.dataServiceDefinition;
    }

    @Override
    @Bean
    public RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer() {
        return helper.createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    @Override
    @Bean(name = FriendlyMetaInfoService.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource entityFriendlyMessageSource(){
        return helper.createFriendlyMessageSource();
    }

    @Override
    @Bean(name = FriendlyMetaInfoService.SERVICE_NAME)
    public FriendlyMetaInfoService friendlyMetaInfoService(){
        return new FriendlyMetaInfoServiceImpl();
    }
    // **************************************************** //
    //  IEntityBeanList                                     //
    // **************************************************** //

    @Override
    @Bean(name = DynamicEntityService.COMPONENT_NAME)
    public DynamicEntityService dynamicEntityService(){
        return new DynamicEntityServiceImpl();
    }

//    @Override
//    @Bean(name = FrontEndDynamicEntityService.SERVICE_NAME)
//    public FrontEndDynamicEntityService dynamicServerEntityService(){
//        return new FrontEndDynamicEntityServiceImpl();
//    }

    @Override
    public DataSource serviceDataSource() {
        return helper.createDefaultDataSource();
    }

    @Override
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return helper.createAnEntityManagerFactory(serviceDataSource(),
                helper.createAPersistenceUnitPostProcessor(
                        runtimeEnvironmentPropertyPlaceholderConfigurer()));
    }

    @Override
    public JpaTransactionManager jpaTransactionManager() {
        return helper.createJpaTransactionManager(entityManagerFactory().getObject());
    }

    @Override
    @Bean(name = PersistenceManagerFactory.COMPONENT_NAME)
    public PersistenceManagerFactory persistenceManagerFactory() {
        return new PersistenceManagerFactory();
    }

    @Override
    @Bean(name = PersistenceManager.COMPONENT_NAME)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PersistenceManager persistenceManager() {
        return new PersistenceManagerImpl();
    }

    @Override
    @Bean(name = PersistenceManagerInvoker.COMPONENT_NAME)
    public PersistenceManagerInvoker persistenceManagerInvoker() {
        return new PersistenceManagerInvoker();
    }

    // **************************************************** //
    //                                                      //
    // **************************************************** //
}