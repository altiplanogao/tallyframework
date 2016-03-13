package com.taoswork.tallybook.dataservice.core.entity;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntityHelper;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class EntityCatalog {
    protected final String resource;
    protected final String entityInterfaceName;

    public EntityCatalog(Class<?> entityInterface) {
        this.entityInterfaceName = entityInterface.getName();
        this.resource = PersistEntityHelper.getEntityName(entityInterface);
    }

    protected EntityCatalog(String resource, String entityInterfaceName) {
        this.resource = resource;
        this.entityInterfaceName = entityInterfaceName;
    }

    public EntityCatalog(EntityCatalog other) {
        this.resource = other.resource;
        this.entityInterfaceName = other.entityInterfaceName;
    }

    public String getResource() {
        return resource;
    }

    public String getEntityInterfaceName() {
        return entityInterfaceName;
    }

}
