package com.taoswork.tallybook.dataservice.jpa.config.helper;

import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.jpa.utils.JPAPropertiesPersistenceUnitPostProcessor;
import com.taoswork.tallybook.general.extension.collections.PropertiesUtility;
import com.taoswork.tallybook.general.solution.property.PropertiesSubCollectionProvider;
import net.sf.ehcache.Ehcache;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public class DataServiceJpaBeanCreationHelper {
    protected final JpaDatasourceDefinition jpaDatasourceDefinition;
    protected final JpaDbSetting dbSetting;

    //The following 2 static final Class name is useless, except avoiding missing dependency.
    protected static final Class<SingletonEhCacheRegionFactory> ensureClzLoaded = null;
    protected static final Class<Ehcache> ensureClzLoaded2 = null;


    public DataServiceJpaBeanCreationHelper(JpaDatasourceDefinition jpaDatasourceDefinition, JpaDbSetting dbSetting) {
        this.jpaDatasourceDefinition = jpaDatasourceDefinition;
        this.dbSetting = dbSetting;
    }

    // **************************************************** //
    //  DataSource                                          //
    // **************************************************** //

    public DataSource createDefaultDataSource() {
        return dbSetting.publishDataSourceWithDefinition(jpaDatasourceDefinition);
    }

    public DataSource createAJndiDataSource() {
        return dbSetting.publishDataSourceWithDefinition(this.jpaDatasourceDefinition);
    }

    // **************************************************** //
    //  EntityManagerFactory                                //
    // **************************************************** //

    public AbstractEntityManagerFactoryBean createDefaultEntityManagerFactory(
            DataSource dataSource,
            PropertiesSubCollectionProvider propertiesProvider) {
        return createAnEntityManagerFactory(dataSource,
                createAPersistenceUnitPostProcessor(propertiesProvider));
    }

    public AbstractEntityManagerFactoryBean createAnEntityManagerFactory(
            DataSource dataSource,
            PersistenceUnitPostProcessor puPostProcessor) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPersistenceXmlLocation(
                jpaDatasourceDefinition.getPersistenceXml());
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPersistenceUnitPostProcessors(puPostProcessor);
        //       entityManagerFactory.setPersistenceXmlLocation("classpath*:/persistence/persistence-admin-tallyuser.xml");
//        entityManagerFactory.setDataSource(hostUserDataSource());
        entityManagerFactory.setPersistenceUnitName(
                jpaDatasourceDefinition.getPersistenceUnit());
        return entityManagerFactory;
    }

    // **************************************************** //
    //  JpaTransactionManager                               //
    // **************************************************** //
    public JpaTransactionManager createJpaTransactionManager(EntityManagerFactory refEmf) {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setEntityManagerFactory(refEmf);
        return jtm;
    }

    // **************************************************** //
    //                                                      //
    // **************************************************** //

    public PersistenceUnitPostProcessor createAPersistenceUnitPostProcessor(PropertiesSubCollectionProvider propertyConfigurer) {
        JPAPropertiesPersistenceUnitPostProcessor postProcessor =
                new JPAPropertiesPersistenceUnitPostProcessor();
        Properties propertiesProperties = propertyConfigurer.getSubProperties("persistence.pu.");
        propertiesProperties.put("persistence.pu.hibernate.dialect", dbSetting.hibernateDialect());

        PropertiesUtility.updateKeyPrefix(propertiesProperties, propertiesProperties,
                "persistence.pu.",
                jpaDatasourceDefinition.getPersistenceUnit() + ".",
                true);

        postProcessor.setPersistenceProps(propertiesProperties);
        return postProcessor;
    }

    public JpaDbSetting getDbSetting() {
        return this.dbSetting;
    }

}
