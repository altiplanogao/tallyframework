package com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter;

import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

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
        EntityTypeParameter entityTypes = new EntityTypeParameter()
            .setTypeName(entityTypeName)
            .setType(entityType)
            .setCeilingType(entityType);
        return entityTypes;
    }

    public static EntityTypeParameter getBy(DataServiceManager dataServiceManager, String entityTypeName, Entity entity) {
        Class typeByName = entityTypeNameToEntityType(dataServiceManager, entityTypeName);
        Class ceilingType = entity.getEntityCeilingType();
        Class type = entity.getEntityType();
        EntityTypeParameter typeParameter = new EntityTypeParameter();
        if(ceilingType == null)
            ceilingType = typeByName;
        if(ceilingType == null){
            ceilingType = type;
        }
        typeParameter.setTypeName(entityTypeName)
            .setCeilingType(ceilingType);
        if(type != null && ceilingType.isAssignableFrom(type)){
            typeParameter.setType(type);
        }
        return typeParameter;
    }
}