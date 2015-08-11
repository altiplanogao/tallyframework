package com.taoswork.tallybook.general.dataservice.support.config.helper;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.HsqlDbSetting;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.general.dataservice.support.config.dbsetting.MysqlDbSetting;
import com.taoswork.tallybook.general.extension.collections.PropertiesUtility;
import com.taoswork.tallybook.general.extension.collections.SetBuilder;
import com.taoswork.tallybook.general.solution.i18n.i18nMessageFileArranger;
import com.taoswork.tallybook.general.solution.jpa.JPAPropertiesPersistenceUnitPostProcessor;
import com.taoswork.tallybook.general.solution.property.PropertiesSubCollectionProvider;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import net.sf.ehcache.Ehcache;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
public class DataServiceBeanCreationHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceBeanCreationHelper.class);

    //The following 2 static final Class name is useless, except avoiding missing dependency.
    protected static final Class<SingletonEhCacheRegionFactory> ensureClzLoaded = null;
    protected static final Class<Ehcache> ensureClzLoaded2 = null;

    protected final IDataServiceDefinition dataServiceDefinition;
    protected final IDbSetting dbSetting;

    // **************************************************** //
    //  Constructor                                         //
    // **************************************************** //

    public DataServiceBeanCreationHelper(IDataServiceDefinition dataServiceDefinition, IDbSetting dbSetting) {
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

    // **************************************************** //
    //  DataSource                                          //
    // **************************************************** //

    public DataSource createDefaultDataSource(){
        return dbSetting.publishDataSourceWithDefinition(dataServiceDefinition);
    }

    public DataSource createAJndiDataSource(){
        return dbSetting.publishDataSourceWithDefinition(this.dataServiceDefinition);
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

    // **************************************************** //
    //  RuntimeEnvironmentPropertyPlaceholderConfigurer     //
    // **************************************************** //

    public RuntimeEnvironmentPropertyPlaceholderConfigurer createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer(){
        return createAPropertyPlaceholderConfigurer(
                new ClassPathResource(dataServiceDefinition.getPropertiesResourceDirectory()));
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

    // **************************************************** //
    //  JpaTransactionManager                               //
    // **************************************************** //
    public JpaTransactionManager createJpaTransactionManager(EntityManagerFactory refEmf) {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setEntityManagerFactory(refEmf);
        return jtm;
    }

    // **************************************************** //
    //  MessageSource                                       //
    // **************************************************** //

    public MessageSource createFriendlyMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<String> basenameList = new ArrayList<String>();
        i18nMessageFileArranger arranger = new i18nMessageFileArranger();

        final String[] messageDirs = dataServiceDefinition.getEntityMessageDirectory().split(
                IDataServiceDefinition.ENTITY_MESSAGES_FILE_DELIMTER);

        for(String messageDir : messageDirs) {
            final String matchPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + messageDir + "*.properties";

            try {
                Resource[] resources = resolver.getResources(
                        //ResourceUtils.CLASSPATH_URL_PREFIX +
                        matchPattern);

                for (Resource res : resources) {
                    try {
                        String respath = res.getFilename();
                        respath = messageDir + respath;
                        arranger.add(respath);
                    } catch (Exception e) {
                        LOGGER.error("Resource '{}' failed to return path.", res.getURI());
                    }
                }
                for (String simplefilename : arranger.fileNamesWithoutLocalization()) {
                    basenameList.add(ResourceUtils.CLASSPATH_URL_PREFIX + simplefilename);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ms.setBasenames(basenameList.toArray(new String[basenameList.size()]));

        return ms;
    }

    // **************************************************** //
    //                                                      //
    // **************************************************** //

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