package com.taoswork.tallybook.general.dataservice.support.config;

import com.taoswork.tallybook.dynamic.dataservice.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.dynamic.dataservice.description.impl.FriendlyMetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.service.impl.DynamicServerEntityServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.service.impl.DynamicEntityServiceImpl;
import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.spring.IDataServiceDelegate;
import com.taoswork.tallybook.general.dataservice.support.config.helper.DataServiceBeanCreationHelper;
import com.taoswork.tallybook.general.dataservice.support.config.list.IDataServiceSupporterBeanList;
import com.taoswork.tallybook.general.dataservice.support.config.list.IEntityBeanList;
import com.taoswork.tallybook.general.dataservice.support.config.list.IGeneralBeanList;
import com.taoswork.tallybook.general.dataservice.support.config.list.IPersistenceBeanList;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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

    @Override
    @Bean(name = DynamicServerEntityService.SERVICE_NAME)
    public DynamicServerEntityService dynamicServerEntityService(){
        return new DynamicServerEntityServiceImpl();
    }

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

    // **************************************************** //
    //                                                      //
    // **************************************************** //
}
