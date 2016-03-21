package com.taoswork.tallybook.dataservice.server.io.request.parameter;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.server.manage.DataServiceManager;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import org.apache.commons.lang3.StringUtils;

public class EntityTypeParameterBuilder {

    private static Class entityTypeNameToEntityType(DataServiceManager dataServiceManager, String entityTypeName) {
        String entityInterfaceType = dataServiceManager.getEntityInterfaceName(entityTypeName);
        if (entityInterfaceType == null)
            entityInterfaceType = entityTypeName;
        try {
            Class directType = Class.forName(entityInterfaceType);
            boolean candidate = Persistable.class.isAssignableFrom(directType);
            if (candidate)
                return directType;
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public static EntityTypeParameter getBy(DataServiceManager dataServiceManager, String entityTypeName) {
        Class entityType = entityTypeNameToEntityType(dataServiceManager, entityTypeName);
        EntityTypeParameter entityTypes = new EntityTypeParameter(entityType, entityType)
                .setTypeName(entityTypeName);
        if (entityType != null) {
            if (StringUtils.equals(entityTypeName, entityType.getName())) {
                String adjustedResName = dataServiceManager.getEntityResourceName(entityTypeName);
                entityTypes.setTypeName(adjustedResName);
            }
        }
        return entityTypes;
    }

    public static EntityTypeParameter getBy(DataServiceManager dataServiceManager, String entityTypeName, Entity entity) {
        Class typeByName = entityTypeNameToEntityType(dataServiceManager, entityTypeName);
        Class ceilingType = entity.getCeilingType();
        Class type = entity.getType();
        if (ceilingType == null)
            ceilingType = typeByName;
        if (ceilingType == null) {
            ceilingType = type;
        }

        if (type != null && ceilingType.isAssignableFrom(type)) {
            EntityTypeParameter typeParameter = new EntityTypeParameter(ceilingType, type);
            typeParameter.setTypeName(entityTypeName);
            return typeParameter;
        }
        return null;
    }
}