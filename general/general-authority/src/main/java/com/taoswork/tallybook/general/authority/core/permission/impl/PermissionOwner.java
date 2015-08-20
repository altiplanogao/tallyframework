package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionOwner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class PermissionOwner implements IPermissionOwner {
    private Map<String, IEntityPermission> entityPermissionMap = new ConcurrentHashMap<String, IEntityPermission>();

    @Override
    public IEntityPermission getEntityPermission(String resourceEntity){
        return entityPermissionMap.getOrDefault(resourceEntity, null);
    }

    @Override
    public IPermissionOwner addEntityPermission(IEntityPermission... entityPermissions){
        for(IEntityPermission perm : entityPermissions){
            entityPermissionMap.put(perm.resourceEntity(), perm);
        }
        return this;
    }

    @Override
    public String toString() {
        return "PermissionOwner{" +
            "entityPermissionMap=" + entityPermissionMap +
            '}';
    }
}
