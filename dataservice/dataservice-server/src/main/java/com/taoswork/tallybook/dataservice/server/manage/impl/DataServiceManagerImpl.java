package com.taoswork.tallybook.dataservice.server.manage.impl;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.core.entity.EntityCatalog;
import com.taoswork.tallybook.dataservice.core.persistence.InputEntityTranslator;
import com.taoswork.tallybook.dataservice.server.manage.DataServiceManager;
import com.taoswork.tallybook.dataservice.server.manage.ManagedEntityCatalog;
import com.taoswork.tallybook.dataservice.server.service.FrontEndEntityTranslator;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class DataServiceManagerImpl implements DataServiceManager, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceManager.class);

    // DataService name to DataService
    private final Map<String, IDataService> dataServiceMap = new HashMap<String, IDataService>();

    private final Map<String, ManagedEntityCatalog> entityTypeNameToCatalogMap = new HashMap<String, ManagedEntityCatalog>();

    private final Map<String, String> entityResNameToTypeName = new HashMap<String, String>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        doInitialize();
    }

    @Override
    public void doInitialize() {
    }

    @Override
    public DataServiceManager buildingAppendDataService(String dataServiceBeanName, IDataService dataService) {
        dataServiceMap.put(dataServiceBeanName, dataService);
        for (Map.Entry<String, EntityCatalog> entityCatalogEntry : dataService.getEntityCatalogs().entrySet()) {
            String typeName = entityCatalogEntry.getKey();
            EntityCatalog entityCatalog = entityCatalogEntry.getValue();
            if (entityTypeNameToCatalogMap.containsKey(typeName)) {
                LOGGER.error("ManagedEntityCatalog with name '{}' already exist, over-writing", typeName);
            }
            entityTypeNameToCatalogMap.put(typeName, new ManagedEntityCatalog(dataServiceBeanName, entityCatalog));
        }
        return this;
    }

    @Override
    public DataServiceManager buildingAnnounceFinishing() {
        for (Map.Entry<String, ManagedEntityCatalog> entryEntry : entityTypeNameToCatalogMap.entrySet()) {
            String interfaceName = entryEntry.getKey();
            EntityCatalog managedEntityCatalog = entryEntry.getValue();

            String resourceName = managedEntityCatalog.getResource();
            if (entityResNameToTypeName.containsKey(resourceName)) {
                LOGGER.error("ResourceName '{}' for interface '{}' already used.", resourceName, interfaceName);
            }
            entityResNameToTypeName.put(resourceName, interfaceName);
        }

        return this;
    }

    @Override
    public ManagedEntityCatalog getInterfaceEntityEntry(String entityType) {
        return entityTypeNameToCatalogMap.get(entityType);
    }

    @Override
    public String getEntityInterfaceName(String resourceName) {
        return entityResNameToTypeName.get(resourceName);
    }

    @Override
    public String getEntityResourceName(String entityType) {
        ManagedEntityCatalog managedEntityCatalog = getInterfaceEntityEntry(entityType);
        if (null != managedEntityCatalog) {
            return managedEntityCatalog.getResource();
        } else {
            return "";
        }
    }

    @Override
    public IDataService getDataServiceByServiceName(String serviceName) {
        return dataServiceMap.get(serviceName);
    }

    @Override
    public IDataService getDataService(String entityType) {
        ManagedEntityCatalog managedEntityCatalog = getInterfaceEntityEntry(entityType);
        if (null != managedEntityCatalog) {
            return dataServiceMap.get(managedEntityCatalog.getDataServiceName());
        } else {
            return null;
        }
    }

    @Override
    public IEntityService getDynamicEntityService(String entityType) {
        IDataService dataService = getDataService(entityType);
        return dataService.getService(IEntityService.COMPONENT_NAME);
    }

    @Override
    public InputEntityTranslator getEntityTranslator() {
        return new FrontEndEntityTranslator();
    }
}
