package com.taoswork.tallybook.testmaterial.jpa.persistence.conf;

import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import com.taoswork.tallybook.testmaterial.jpa.JpaTestSetting;
import com.taoswork.tallybook.testmaterial.jpa.database.JpaTestDataSourceCreator;
import com.taoswork.tallybook.testmaterial.jpa.database.derby.DerbyTestDbCreator;
import com.taoswork.tallybook.testmaterial.jpa.database.mysql.MysqlTestDbCreator;
import org.hibernate.dialect.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
@EnableTransactionManagement
public abstract class TestDbPersistenceConfigBase {
    protected boolean useMysql() {
        return JpaTestSetting.useMysql();
    }

    public abstract String getPersistenceXml();

    public abstract String getDataSourceName();

    public abstract String getPuName();

    @Bean
    public JpaTestDataSourceCreator.ITestDbCreator theDbCreator() {
        if (useMysql()) {
            return new MysqlTestDbCreator();
        }
        return new DerbyTestDbCreator();
//        return new MysqlTestDbCreator();
//        return new HsqlTestDbCreator();
    }

    @Bean
    BeanCreationMonitor beanCreationMonitor() {
        return new BeanCreationMonitor("TestApplicationContext");
    }

    @Bean
    DataSource testDbDataSource() {
        return theDbCreator().createDataSource(getDataSourceName());
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPersistenceXmlLocation(
                "classpath:/META-INF/persistence/" + getPersistenceXml());
        entityManagerFactory.setDataSource(testDbDataSource());

        Class dialect = theDbCreator().getDialectClass();
        PersistenceUnitPostProcessor puPostProcessor = createPersistenceUnitPostProcessor(dialect);
        entityManagerFactory.setPersistenceUnitPostProcessors(puPostProcessor);
        //       entityManagerFactory.setPersistenceXmlLocation("classpath*:/persistence/persistence-admin-tallyuser.xml");
//        entityManagerFactory.setDataSource(hostUserDataSource());
        entityManagerFactory.setPersistenceUnitName(getPuName());
        return entityManagerFactory;
    }

    @Bean
    protected JpaTransactionManager helperCreateJpaTransactionManager() {
        JpaTransactionManager jtm = new JpaTransactionManager();
        //jtm.setEntityManagerFactory(entityManagerFacto_ry());
        jtm.setEntityManagerFactory(entityManagerFactory().getObject());
        return jtm;
    }

    public static PersistenceUnitPostProcessor createPersistenceUnitPostProcessor(final Class<? extends Dialect> dialect) {
        PersistenceUnitPostProcessor postProcessor =
                new PersistenceUnitPostProcessor() {
                    @Override
                    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
                        Properties props = new Properties();
                        putProperty(props, "");
                        putProperty(props, "hibernate.hbm2ddl.auto=create-drop");
                        putProperty(props, "hibernate.dialect=" + dialect.getName());
                        putProperty(props, "hibernate.connection.useUnicode=true");
                        putProperty(props, "hibernate.connection.characterEncoding=UTF-8");
                        putProperty(props, "hibernate.connection.charSet=UTF-8");
                        putProperty(props, "hibernate.cache.region.factory_class=" + org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory.class.getName());
                        putProperty(props, "hibernate.cache.use_second_level_cache=true");
                        putProperty(props, "hibernate.cache.use_query_cache=true");
                        putProperty(props, "hibernate.show_sql=false");

                        pui.setProperties(props);
                    }

                    private void putProperty(Properties props, String prop) {
                        String[] strs = prop.split("=");
                        if (strs == null || strs.length != 2) {
                            return;
                        }
                        props.put(strs[0], strs[1]);
                    }
                };
        return postProcessor;
    }
}
