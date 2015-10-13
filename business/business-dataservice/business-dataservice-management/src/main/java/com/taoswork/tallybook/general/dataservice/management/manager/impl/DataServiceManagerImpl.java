package com.taoswork.tallybook.general.dataservice.management.manager.impl;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.EntityEntry;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.dataservice.management.manager.ManagedEntityEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class DataServiceManagerImpl implements DataServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceManager.class);

    // DataService name to DataService
    private final Map<String, IDataService> dataServiceMap = new HashMap<String, IDataService>();
    private final Map<String, ManagedEntityEntry> entityTypeNameToEntryMap = new HashMap<String, ManagedEntityEntry>();

    private final Map<String, String> entityResNameToTypeName = new HashMap<String, String>();

    @Override
    public DataServiceManager buildingAppendDataService(String dataServiceBeanName, IDataService dataService){
        dataServiceMap.put(dataServiceBeanName, dataService);
        for(Map.Entry<String, EntityEntry> entryEntryElement : dataService.getEntityEntries().entrySet()){
            String typeName = entryEntryElement.getKey();
            EntityEntry entityEntry = entryEntryElement.getValue();
            if(entityTypeNameToEntryMap.containsKey(typeName)){
                LOGGER.error("ManagedEntityEntry with name '{}' already exist, over-writing", typeName);
            }
            entityTypeNameToEntryMap.put(typeName, new ManagedEntityEntry(dataServiceBeanName, entityEntry));
        }
        return this;
    }

    @Override
    public DataServiceManager buildingAnnounceFinishing(){
        for(Map.Entry<String, ManagedEntityEntry> entryEntry : entityTypeNameToEntryMap.entrySet()){
            String interfaceName = entryEntry.getKey();
            EntityEntry managedEntityEntry = entryEntry.getValue().getEntityEntry();

            String resourceName = managedEntityEntry.getResourceName();
            if(entityResNameToTypeName.containsKey(resourceName)){
                LOGGER.error("ResourceName '{}' for interface '{}' already used.", resourceName, interfaceName);
            }
            entityResNameToTypeName.put(resourceName, interfaceName);
        }

        return this;
    }

    @Override
    public ManagedEntityEntry getInterfaceEntityEntry(String entityType){
        return entityTypeNameToEntryMap.getOrDefault(entityType, null);
    }

    @Override
    public String getEntityInterfaceName(String resourceName) {
        return entityResNameToTypeName.getOrDefault(resourceName, null);
    }

    @Override
    public String getEntityResourceName(String entityType) {
        ManagedEntityEntry managedEntityEntry = getInterfaceEntityEntry(entityType);
        if (null != managedEntityEntry) {
            return managedEntityEntry.getEntityEntry().getResourceName();
        } else {
            return "";
        }
    }

    @Override
    public IDataService getDataService(String entityType) {
        ManagedEntityEntry managedEntityEntry = getInterfaceEntityEntry(entityType);
        if (null != managedEntityEntry) {
            return dataServiceMap.getOrDefault(managedEntityEntry.getDataServiceName(), null);
        } else {
            return null;
        }
    }

    @Override
    public DynamicEntityService getDynamicEntityService(String entityType) {
        IDataService dataService = getDataService(entityType);
        return dataService.getService(DynamicEntityService.COMPONENT_NAME);
    }
}
