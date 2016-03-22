package com.taoswork.tallybook.dataservice.jpa.config;

import com.taoswork.tallybook.dataservice.IDatasourceBeanConfiguration;
import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.config.beanlist.IEntityBeanList;
import com.taoswork.tallybook.dataservice.jpa.config.beanlist.IPersistenceBeanList;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.MysqlDbSetting;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.TestDbSetting;
import com.taoswork.tallybook.dataservice.jpa.config.helper.DataServiceJpaBeanCreationHelper;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.PersistenceService;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.OpenEntityManagerAop;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.impl.PersistenceServiceImpl;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.impl.JpaEntityServiceImpl;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.PersistenceManagerFactory;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallybook.dataservice.jpa.core.persistence.impl.PersistenceManagerImpl;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
@Configuration
public abstract class JpaDatasourceBeanConfiguration
        implements
        IDatasourceBeanConfiguration,
        IEntityBeanList,
        IPersistenceBeanList {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaDatasourceBeanConfiguration.class);

    @Autowired
    RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer;

    private final JpaDatasourceDefinition jpaDatasourceDefinition;
    protected JpaDbSetting dbSetting;
    protected DataServiceJpaBeanCreationHelper helper;

    // **************************************************** //
    //  Constructor & initialize                            //
    // **************************************************** //

    public JpaDatasourceBeanConfiguration() {
        this.jpaDatasourceDefinition = createJpaDataDefinition();
    }

    @Autowired
    public void setDbSetting(JpaDbSetting dbSetting) {
        this.dbSetting = setProperSetting(dbSetting);
        this.helper = new DataServiceJpaBeanCreationHelper(jpaDatasourceDefinition, this.dbSetting);
    }

    protected abstract JpaDatasourceDefinition createJpaDataDefinition();

    public JpaDbSetting getDbSetting() {
        return dbSetting;
    }

    public JpaDatasourceDefinition getJpaDatasourceDefinition() {
        return jpaDatasourceDefinition;
    }

    private static JpaDbSetting setProperSetting(JpaDbSetting dbSetting) {
        if (dbSetting != null) {
            return dbSetting;
        } else {
            if (System.getProperty("usehsql", "false").equals("true")) {
                return new TestDbSetting();
            } else {
                return new MysqlDbSetting();
            }
        }
    }

    /**
     * *********************************************************
     * IEntityBeanList                                          *
     * ({@link IEntityBeanList})
     * **********************************************************
     */
    @Override
    @Bean(name = IEntityService.COMPONENT_NAME)
    public JpaEntityService dynamicEntityService() {
        return new JpaEntityServiceImpl();
    }

    @Override
    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
        return new AnnotationAwareAspectJAutoProxyCreator();
    }

    @Override
    @Bean(name = PersistenceService.COMPONENT_NAME)
    public PersistenceService dynamicEntityPersistenceService() {
        return new PersistenceServiceImpl();
    }
    /* IEntityBeanList                                          */

    /**
     * *********************************************************
     * IPersistenceBeanList                                     *
     * ({@link IPersistenceBeanList})
     * **********************************************************
     */

    @Override
    @Bean(name = JpaDatasourceDefinition.DATA_SERVICE_DEFINITION_BEAN_NAME)
    public JpaDatasourceDefinition dataServiceDefinitionBean() {
        return this.jpaDatasourceDefinition;
    }

    @Override
    //NOT A BEAN, need to be override and annotated with @Bean
    public DataSource serviceDataSource() {
        return helper.createDefaultDataSource();
    }

    @Override
    //NOT A BEAN, need to be override and annotated with @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return helper.createAnEntityManagerFactory(serviceDataSource(),
                helper.createAPersistenceUnitPostProcessor(
                        runtimeEnvironmentPropertyPlaceholderConfigurer));
    }

    @Override
    //NOT A BEAN, need to be override and annotated with @Bean
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

    @Override
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OpenEntityManagerAop openEntityManagerAop() {
        return new OpenEntityManagerAop();
    }

    /* IPersistenceBeanList                                     */

    // **************************************************** //
    //                                                      //
    // **************************************************** //
}
