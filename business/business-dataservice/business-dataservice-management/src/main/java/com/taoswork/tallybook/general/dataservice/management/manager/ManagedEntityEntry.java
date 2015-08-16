package com.taoswork.tallybook.general.dataservice.management.manager;

import com.taoswork.tallybook.dynamic.dataservice.entity.EntityEntry;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class ManagedEntityEntry {

    protected final String dataServiceName;
    protected final EntityEntry entityEntry;

    public ManagedEntityEntry(String dataServiceName, Class<?> entityInterface) {
        this.dataServiceName = dataServiceName;
        this.entityEntry = new EntityEntry(entityInterface);
    }

    public ManagedEntityEntry(String dataServiceName, EntityEntry entityEntry) {
        this.dataServiceName = dataServiceName;
        this.entityEntry = entityEntry;
    }

    public ManagedEntityEntry(String dataServiceName,
                              String entityInterfaceName,
                              String friendlyName) {
        this(dataServiceName, new EntityEntry(entityInterfaceName, friendlyName));
    }

    public String getDataServiceName() {
        return dataServiceName;
    }

    public EntityEntry getEntityEntry() {
        return entityEntry;
    }
}
