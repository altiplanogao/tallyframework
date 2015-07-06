package com.taoswork.tallybook.general.dataservice.support.impl;

import com.taoswork.tallybook.dynamic.dataservice.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.dataservice.support.IDataService;
import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.dataservice.support.spring.IDataServiceDelegate;
import com.taoswork.tallybook.general.dataservice.support.config.ADataServiceBeanConfiguration;
import com.taoswork.tallybook.general.dataservice.support.entity.EntityEntry;
import com.taoswork.tallybook.general.extension.utils.StringUtility;
import com.taoswork.tallybook.general.solution.cache.ehcache.CachedRepoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/11.
 */
public abstract class DataServiceBase implements IDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceBase.class);
    private static final Map<String, Integer> loadCounter = new HashMap<String, Integer>();

    private final IDataServiceDefinition dataServiceDefinition;

    private final Map<String, String> entityResourceNameOverride = new HashMap<String, String>();
    private final Map<String, String> resourceNameToEntityInterface = new HashMap<String, String>();
    protected ApplicationContext applicationContext;
    //key is interface name
    private Map<String, EntityEntry> entityEntryMap;

    public DataServiceBase(
            IDataServiceDefinition dataServiceDefinition,
            Class<? extends ADataServiceBeanConfiguration> dataServiceConf,
            List<Class> annotatedClasses) {
        this.dataServiceDefinition = dataServiceDefinition;

        List<Class> annotatedClassesList = new ArrayList<Class>();
        annotatedClassesList.add(dataServiceConf);
        if(annotatedClasses != null) {
            for (Class ac : annotatedClasses) {
                annotatedClassesList.add(ac);
            }
        }

        load(annotatedClassesList);
    }

    public DataServiceBase(
            IDataServiceDefinition dataServiceDefinition,
            List<Class> annotatedClasses) {
        this.dataServiceDefinition = dataServiceDefinition;

        load(annotatedClasses);
    }


    private void load(
            List<Class> annotatedClasses) {
        String clzName = this.getClass().getName();
        int oldCount = loadCounter.getOrDefault(clzName, 0).intValue();
        loadCounter.put(clzName, oldCount + 1);

        loadAnnotatedClasses(annotatedClasses.toArray(new Class[annotatedClasses.size()]));
    }

    private void loadAnnotatedClasses(Class<?>... annotatedClasses) {
        onServiceStart();

        class DataServiceInsideAnnotationConfigApplicationContext
                extends AnnotationConfigApplicationContext
                implements IDataServiceDelegate {
            @Override
            protected void onClose() {
                super.onClose();
                onServiceStop();
            }

            @Override
            public IDataServiceDefinition getDataServiceDefinition() {
                return dataServiceDefinition;
            }
        }

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new DataServiceInsideAnnotationConfigApplicationContext();

        annotationConfigApplicationContext.setDisplayName(this.getClass().getSimpleName());
        annotationConfigApplicationContext.register(annotatedClasses);
        annotationConfigApplicationContext.refresh();

//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(annotatedClasses);
//        annotationConfigApplicationContext.setDisplayName(this.getClass().getSimpleName());
        applicationContext = annotationConfigApplicationContext;

        postConstruct();

        postLoadCheck();
    }

    protected void postConstruct() {
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

    private void postLoadCheck() {
        DynamicEntityDao dynamicEntityDao = getService(DynamicEntityDao.COMPONENT_NAME);
        if (dynamicEntityDao == null) {
            LOGGER.error("Bean '{}' not defined, dynamic entity service may not work.", DynamicEntityDao.COMPONENT_NAME);
        }

        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        if (dynamicEntityMetadataAccess == null) {
            LOGGER.error("Bean '{}' not defined, dynamic entity service may not work.", DynamicEntityMetadataAccess.COMPONENT_NAME);
        }
    }

    @Override
    public Map<String, EntityEntry> getEntityEntries() {
        if (entityEntryMap == null) {
            entityEntryMap = new HashMap<String, EntityEntry>();

            DynamicEntityMetadataAccess dynamicEntityMetadataAccess = getService(DynamicEntityMetadataAccess.COMPONENT_NAME);

            for (Class entityInterface : dynamicEntityMetadataAccess.getAllEntityInterfaces()) {
                String interfaceName = entityInterface.getName();
                if (entityEntryMap.containsKey(interfaceName)) {
                    LOGGER.error("EntityEntry with name '{}' already exist, over-writing", interfaceName);
                }
                Class interfaceClz = null;
                try {
                    interfaceClz = Class.forName(interfaceName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                EntityEntry entityEntry = new EntityEntry(interfaceClz);
                String newResourceName = entityResourceNameOverride.getOrDefault(interfaceName, entityEntry.getResourceName());
                entityEntry.setResourceName(newResourceName);

                entityEntryMap.put(interfaceName, entityEntry);
                resourceNameToEntityInterface.put(newResourceName, interfaceName);
            }
        }
        return Collections.unmodifiableMap(entityEntryMap);
    }

    @Override
    public String getEntityResourceName(String interfaceName) {
        Map<String, EntityEntry> entityEntries = getEntityEntries();
        EntityEntry entityEntry = entityEntries.getOrDefault(interfaceName, null);
        if (entityEntry == null) {
            return null;
        }
        return entityEntry.getResourceName();
    }

    @Override
    public String getEntityInterfaceName(String resourceName) {
        return resourceNameToEntityInterface.getOrDefault(resourceName, null);
    }

    protected final void setEntityResourceNameOverride(Class interfaceClz, String resourceName) {
        setEntityResourceNameOverride(interfaceClz.getName(), resourceName);
    }

    protected final void setEntityResourceNameOverride(String interfaceName, String resourceName) {
        if (StringUtils.isEmpty(resourceName)) {
            entityResourceNameOverride.remove(interfaceName);
        } else {
            entityResourceNameOverride.put(interfaceName, resourceName);
        }
    }
}
