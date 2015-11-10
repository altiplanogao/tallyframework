package com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter;

import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

public class EntityTypeParameterBuilder {

    public static EntityTypeParameter getBy(DataServiceManager dataServiceManager, String entityTypeName) {
        Class entityType = entityTypeNameToEntityType(dataServiceManager, entityTypeName);
        EntityTypeParameter entityTypes = new EntityTypeParameter()
            .setTypeName(entityTypeName)
            .setType(entityType)
            .setCeilingType(entityType);
        return entityTypes;
    }

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

    public static EntityTypeParameter getBy(DataServiceManager dataServiceManager, String entityTypeName, Entity entityForm) {
        Class typeByName = entityTypeNameToEntityType(dataServiceManager, entityTypeName);
        Class ceilingType = entityForm.getEntityCeilingType();
        Class type = entityForm.getEntityType();
        EntityTypeParameter typeParameter = new EntityTypeParameter();
        if(type == null)
            return typeParameter;
        if(ceilingType == null)
            ceilingType = typeByName;
        if(ceilingType == null){
            ceilingType = type;
        }
        if(ceilingType.isAssignableFrom(type)){
            typeParameter.setTypeName(entityTypeName)
                .setType(type).setCeilingType(ceilingType);
        }
        return typeParameter;
    }
}