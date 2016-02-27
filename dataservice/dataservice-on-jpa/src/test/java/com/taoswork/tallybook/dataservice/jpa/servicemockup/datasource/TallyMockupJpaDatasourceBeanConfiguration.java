package com.taoswork.tallybook.dataservice.jpa.servicemockup.datasource;

import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.config.JpaDatasourceBeanConfiguration;
import com.taoswork.tallybook.dataservice.jpa.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.testmaterial.jpa.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
@Configuration
@ComponentScan(
        basePackageClasses = TallyMockupDataService.class,
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
public class TallyMockupJpaDatasourceBeanConfiguration extends JpaDatasourceBeanConfiguration {

    @Override
    protected JpaDatasourceDefinition createJpaDataDefinition() {
        return new TallyMockupJpaDatasourceDefinition();
    }

    @Override
    @Bean(name = TallyMockupJpaDatasourceDefinition.TMOCKUP_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyMockupJpaDatasourceDefinition.TMOCKUP_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        AbstractEntityManagerFactoryBean entityManagerFactory = super.entityManagerFactory();
        Class dialect = helper.getDbSetting().hibernateDialect();
        ((LocalContainerEntityManagerFactoryBean) entityManagerFactory).setPersistenceUnitPostProcessors(
                TestDbPersistenceConfigBase.createPersistenceUnitPostProcessor(dialect)
        );
        return entityManagerFactory;
    }

    @Override
    @Bean(name = TallyMockupJpaDatasourceDefinition.TMOCKUP_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}
