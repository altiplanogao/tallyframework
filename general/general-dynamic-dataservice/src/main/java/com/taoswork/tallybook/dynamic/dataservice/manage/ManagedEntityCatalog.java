package com.taoswork.tallybook.dynamic.dataservice.manage;

import com.taoswork.tallybook.dynamic.dataservice.entity.EntityCatalog;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class ManagedEntityCatalog extends EntityCatalog{

    protected final String dataServiceName;

    public ManagedEntityCatalog(String dataServiceName, Class<?> entityInterface) {
        super(entityInterface);
        this.dataServiceName = dataServiceName;
    }

    public ManagedEntityCatalog(String dataServiceName, EntityCatalog entityCatalog) {
        super(entityCatalog.getResourceName(), entityCatalog.getEntityInterfaceName());
        this.dataServiceName = dataServiceName;
    }

    public String getDataServiceName() {
        return dataServiceName;
    }
}
