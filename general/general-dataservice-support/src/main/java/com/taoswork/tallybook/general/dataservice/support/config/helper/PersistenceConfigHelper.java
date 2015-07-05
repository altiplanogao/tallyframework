package com.taoswork.tallybook.general.dataservice.support.config.helper;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.HsqlDbSetting;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.MysqlDbSetting;
import com.taoswork.tallybook.general.extension.collections.PropertiesUtility;
import com.taoswork.tallybook.general.extension.collections.SetBuilder;
import com.taoswork.tallybook.general.solution.jpa.JPAPropertiesPersistenceUnitPostProcessor;
import com.taoswork.tallybook.general.solution.property.PropertiesSubCollectionProvider;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import net.sf.ehcache.Ehcache;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
public class PersistenceConfigHelper {

    //The following 2 static final Class name is useless, except avoiding missing dependency.
    protected static final Class<SingletonEhCacheRegionFactory> ensureClzLoaded = null;
    protected static final Class<Ehcache> ensureClzLoaded2 = null;

    protected final IDataServiceDefinition dataServiceDefinition;
    protected final IDbSetting dbSetting;

    public PersistenceConfigHelper(IDataServiceDefinition dataServiceDefinition, IDbSetting dbSetting) {
        this.dataServiceDefinition = dataServiceDefinition;
        if (dbSetting != null) {
            this.dbSetting = dbSetting;
        } else {
            if (System.getProperty("usehsql", "false").equals("true")) {
                this.dbSetting = new HsqlDbSetting();
            } else {
                this.dbSetting = new MysqlDbSetting();
            }
        }
    }

    public RuntimeEnvironmentPropertyPlaceholderConfigurer createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer(){
        return createAPropertyPlaceholderConfigurer(
                new ClassPathResource(dataServiceDefinition.getPropertiesResourceDirectory()));
    }

    public DataSource createDefaultDataSource(){
        return dbSetting.publishDataSourceWithDefinition(dataServiceDefinition);
    }

    public AbstractEntityManagerFactoryBean createDefaultEntityManagerFactory(
            DataSource dataSource,
            PropertiesSubCollectionProvider propertiesProvider) {
        return createAnEntityManagerFactory(dataSource,
                createAPersistenceUnitPostProcessor(propertiesProvider));
    }

    /////////////////////////////////////////////////////

    public IDataServiceDefinition getDefinition(){
        return dataServiceDefinition;
    }

    public DataSource createAJndiDataSource(){
        return dbSetting.publishDataSourceWithDefinition(this.dataServiceDefinition);
    }

    public AbstractEntityManagerFactoryBean createAnEntityManagerFactory(
            DataSource dataSource,
            PersistenceUnitPostProcessor puPostProcessor){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPersistenceXmlLocation(
                dataServiceDefinition.getPersistenceXml());
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPersistenceUnitPostProcessors(puPostProcessor);
        //       entityManagerFactory.setPersistenceXmlLocation("classpath*:/persistence/persistence-admin-tallyuser.xml");
//        entityManagerFactory.setDataSource(hostUserDataSource());
        entityManagerFactory.setPersistenceUnitName(
                dataServiceDefinition.getPersistenceUnit());
        return entityManagerFactory;
    }

    public RuntimeEnvironmentPropertyPlaceholderConfigurer createAPropertyPlaceholderConfigurer(Resource resourcePath, Resource... overrideResources){
        if(resourcePath instanceof ClassPathResource){
            ClassPathResource classPathResource = (ClassPathResource)resourcePath;
            if(!classPathResource.getPath().endsWith("/")){
                resourcePath = new ClassPathResource(classPathResource.getPath() + "/");
            }
        }
        RuntimeEnvironmentPropertyPlaceholderConfigurer configurer = new RuntimeEnvironmentPropertyPlaceholderConfigurer();
        configurer.setPropertyPathResources(new SetBuilder<Resource>().append(resourcePath));
        configurer.setOverrideFileResources(overrideResources);
        return configurer;
    }

    public PersistenceUnitPostProcessor createAPersistenceUnitPostProcessor(PropertiesSubCollectionProvider propertyConfigurer){
        JPAPropertiesPersistenceUnitPostProcessor postProcessor =
                new JPAPropertiesPersistenceUnitPostProcessor();
        Properties propertiesProperties = propertyConfigurer.getSubProperties("persistence.pu.");
        propertiesProperties.put("persistence.pu.hibernate.dialect", dbSetting.hibernateDialect());

        PropertiesUtility.updateKeyPrefix(propertiesProperties, propertiesProperties,
                "persistence.pu.",
                dataServiceDefinition.getPersistenceUnit() + ".",
                true);

        postProcessor.setPersistenceProps(propertiesProperties);
        return postProcessor;
    }

}
