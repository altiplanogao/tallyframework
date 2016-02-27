package com.taoswork.tallybook.business.dataservice.tallyuser.conf;

import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.config.JpaDatasourceBeanConfiguration;
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
public class TallyUserJpaDatasourceBeanConfiguration extends JpaDatasourceBeanConfiguration {

    @Override
    protected JpaDatasourceDefinition createJpaDataDefinition() {
        return new TallyUserJpaDatasourceDefinition();
    }

    @Override
    @Bean(name = TallyUserJpaDatasourceDefinition.TUSER_DATASOURCE_NAME)
    public DataSource serviceDataSource() {
        return super.serviceDataSource();
    }

    @Override
    @Bean(name = TallyUserJpaDatasourceDefinition.TUSER_ENTITY_MANAGER_FACTORY_NAME)
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Override
    @Bean(name = TallyUserJpaDatasourceDefinition.TUSER_TRANSACTION_MANAGER_NAME)
    public JpaTransactionManager jpaTransactionManager() {
        return super.jpaTransactionManager();
    }
}
