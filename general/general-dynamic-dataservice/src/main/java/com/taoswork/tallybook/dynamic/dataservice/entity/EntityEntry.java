package com.taoswork.tallybook.dynamic.dataservice.entity;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class EntityEntry {
    protected final String entityInterfaceName;
    protected String resourceName;

    public EntityEntry(Class<?> entityInterface) {
        this(entityInterface.getName(), entityInterface.getSimpleName().toLowerCase());
    }

    public EntityEntry(String entityInterfaceName, String resourceName) {
        this.entityInterfaceName = entityInterfaceName;
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEntityInterfaceName() {
        return entityInterfaceName;
    }

}
