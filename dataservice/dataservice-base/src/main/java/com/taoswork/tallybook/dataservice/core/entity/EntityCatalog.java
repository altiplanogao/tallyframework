package com.taoswork.tallybook.dataservice.core.entity;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntityHelper;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class EntityCatalog {
    protected final String resourceName;
    protected final String entityInterfaceName;

    public EntityCatalog(Class<?> entityInterface) {
        this.entityInterfaceName = entityInterface.getName();
        this.resourceName = PersistEntityHelper.getEntityName(entityInterface);
    }

    protected EntityCatalog(String resourceName, String entityInterfaceName) {
        this.resourceName = resourceName;
        this.entityInterfaceName = entityInterfaceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getEntityInterfaceName() {
        return entityInterfaceName;
    }

}
