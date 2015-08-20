package com.taoswork.tallybook.general.authority.core.permission;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IPermissionOwner {
    IEntityPermission getEntityPermission(String resourceEntity);

    IPermissionOwner addEntityPermission(IEntityPermission... entityPermissions);
}
