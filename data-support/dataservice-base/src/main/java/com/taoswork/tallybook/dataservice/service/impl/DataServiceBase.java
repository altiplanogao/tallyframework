package com.taoswork.tallybook.dataservice.service.impl;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dataservice.IDataServiceDelegate;
import com.taoswork.tallybook.dataservice.config.DataServiceBeanBaseConfiguration;
import com.taoswork.tallybook.dataservice.core.entity.EntityCatalog;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;
import com.taoswork.tallybook.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.general.extension.utils.StringUtility;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallybook.general.solution.time.TimeCounter;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/11.
 */
public abstract class DataServiceBase implements IDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceBase.class);
    private static final Map<String, Integer> loadCounter = new HashMap<String, Integer>();

    private ApplicationContext applicationContext;
    private final IDataServiceDefinition dsDef;

    private final Map<String, String> entityResNameToTypeName = new HashMap<String, String>();
    //key is type name
    private Map<String, EntityCatalog> entityTypeNameToCatalogs;

    public DataServiceBase(
            IDataServiceDefinition dsDef,
            List<Class> annotatedClasses) {
        TimeCounter counter = new TimeCounter();
        this.dsDef = dsDef;
        List<Class> annotatedClassesList = new ArrayList<Class>();

        annotatedClassesList.add(DataServiceBeanBaseConfiguration.class);

        if (annotatedClasses != null) {
            for (Class ac : annotatedClasses) {
                annotatedClassesList.add(ac);
            }
        }

        Class[] dbRelatedConf = this.dsDef.getExtraConfig();
        if (dbRelatedConf != null) {
            for (Class ac : dbRelatedConf) {
                annotatedClassesList.add(ac);
            }
        }

        load(annotatedClassesList);
        double passedSeconds = counter.getPassedInSeconds();
        LOGGER.info("Load DataService {} cost {} seconds.", dsDef.getDataServiceName(), passedSeconds);
    }


    private void load(
            List<Class> annotatedClasses) {
        String clzName = this.getClass().getName();
        int oldCount = MapUtils.getIntValue(loadCounter, clzName, 0);
        loadCounter.put(clzName, oldCount + 1);

        loadAnnotatedClasses(annotatedClasses.toArray(new Class[annotatedClasses.size()]));
    }

    private void loadAnnotatedClasses(Class<?>... annotatedClasses) {
        try {
            onServiceStart();

            class DataServiceInsideAnnotationConfigApplicationContext
                    extends AnnotationConfigApplicationContext
                    implements IDataServiceDelegate {
                private IDataService dataService = null;

                @Override
                protected void onClose() {
                    super.onClose();
                    onServiceStop();
                }

                @Override
                public IDataServiceDefinition getDataServiceDefinition() {
                    return dsDef;
                }

                @Override
                public IDataService theDataService() {
                    return dataService;
                }

                public void setDataService(IDataService dataService) {
                    this.dataService = dataService;
                }
            }

            DataServiceInsideAnnotationConfigApplicationContext annotationConfigApplicationContext = new DataServiceInsideAnnotationConfigApplicationContext();

            annotationConfigApplicationContext.setDisplayName(this.getClass().getSimpleName());
            annotationConfigApplicationContext.register(annotatedClasses);
            annotationConfigApplicationContext.refresh();

            annotationConfigApplicationContext.setDataService(this);

//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(annotatedClasses);
//        annotationConfigApplicationContext.setDisplayName(this.getClass().getSimpleName());
            applicationContext = annotationConfigApplicationContext;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            postConstruct();
            postLoadCheck();
        }
    }

    protected void postConstruct() {
    }

    private void postLoadCheck() {
//        DynamicEntityDao dynamicEntityDao = getService(DynamicEntityDao.COMPONENT_NAME);
//        if (dynamicEntityDao == null) {
//            LOGGER.error("Bean '{}' not defined, dynamic entity service may not work.", DynamicEntityDao.COMPONENT_NAME);
//        }

        EntityMetaAccess entityMetaAccess = getService(EntityMetaAccess.COMPONENT_NAME);
        if (entityMetaAccess == null) {
            LOGGER.error("Bean '{}' not defined, dynamic entity service may not work.", EntityMetaAccess.COMPONENT_NAME);
        }
    }

    protected void onServiceStart() {
        CachedRepoManager.startEhcache();
    }

    protected void onServiceStop() {
        CachedRepoManager.stopEhcache();
    }

    @Override
    public <T> T getService(String serviceName) {
        return applicationContext == null ?
                null : (T) applicationContext.getBean(serviceName);
    }

    @Override
    public <T> T getService(Class<T> clz, String serviceName) {
        return applicationContext == null ?
                null : (T) applicationContext.getBean(serviceName);
    }

    @Override
    public <T> T getService(Class<T> serviceCls) {
        Field[] fields = serviceCls.getDeclaredFields();
        int matchingField = 0;
        Field fitFiled = null;
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            boolean isFieldFit = field.getType().equals(String.class) &&
                    Modifier.isStatic(modifiers) &&
                    Modifier.isPublic(modifiers) &&
                    Modifier.isFinal(modifiers);
            if (isFieldFit) {
                matchingField++;
                fitFiled = field;
            }
        }

        String serviceName = "";//serviceCls.getSimpleName();
        try {
            if (matchingField == 1) {
                serviceName = (String) fitFiled.get(null);
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e2) {
        }

        String determinBeanName = "";

        NoSuchBeanDefinitionException noSuchBeanDefinitionException = null;

        if (!serviceName.equals("")) {
            if (applicationContext.containsBean(serviceName)) {
                determinBeanName = serviceName;
            } else {
                String clzName = serviceCls.getSimpleName();
                serviceName = clzName;
                if (applicationContext.containsBean(clzName)) {
                    determinBeanName = clzName;
                } else {
                    clzName = StringUtility.changeFirstCharUpperLowerCase(clzName);
                    determinBeanName = clzName;
                }
            }
        }

        return getService(determinBeanName);
    }

    @Override
    public IDataServiceDefinition getDataServiceDefinition() {
        return getService(IDataServiceDefinition.DATA_SERVICE_DEFINITION_BEAN_NAME);
    }

    @Override
    public Map<String, EntityCatalog> getEntityCatalogs() {
        if (entityTypeNameToCatalogs == null) {
            entityTypeNameToCatalogs = new HashMap<String, EntityCatalog>();

            EntityMetaAccess entityMetaAccess = getService(EntityMetaAccess.COMPONENT_NAME);

            for (Class entityType : entityMetaAccess.getAllEntities(true, true)) {
                String typeName = entityType.getName();
                if (entityTypeNameToCatalogs.containsKey(typeName)) {
                    LOGGER.error("EntityCatalog with name '{}' already exist, over-writing", typeName);
                }
                EntityCatalog entityCatalog = new EntityCatalog(entityType);
                String newResourceName = entityCatalog.getResource();

                entityTypeNameToCatalogs.put(typeName, entityCatalog);
                entityResNameToTypeName.put(newResourceName, typeName);
            }
        }
        return Collections.unmodifiableMap(entityTypeNameToCatalogs);
    }

    @Override
    public String getEntityResourceName(String typeName) {
        Map<String, EntityCatalog> entityEntries = getEntityCatalogs();
        EntityCatalog entityCatalog = entityEntries.get(typeName);
        if (entityCatalog == null) {
            return null;
        }
        return entityCatalog.getResource();
    }

    @Override
    public String getEntityTypeName(String resourceName) {
        return entityResNameToTypeName.get(resourceName);
    }

    @Override
    public void setSecurityVerifier(ISecurityVerifier securityVerifier) {
        SecurityVerifierAgent securityVerifierAgent =
                getService(SecurityVerifierAgent.COMPONENT_NAME);
        securityVerifierAgent.setVerifier(securityVerifier);
    }
}
