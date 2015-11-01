package com.taoswork.tallybook.dynamic.dataservice.entity;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class EntityEntry {
    protected final String resourceName;
    protected final String entityInterfaceName;

    public EntityEntry(Class<?> entityInterface) {
        this.entityInterfaceName = entityInterface.getName();
        String typeName = entityInterface.getSimpleName().toLowerCase();
        PersistEntity persistEntity = entityInterface.getDeclaredAnnotation(PersistEntity.class);
        if(persistEntity != null){
            String nameOverride = persistEntity.nameOverride();
            if(StringUtils.isNotEmpty(nameOverride))
                typeName = nameOverride;
        }
        this.resourceName = typeName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getEntityInterfaceName() {
        return entityInterfaceName;
    }

}
